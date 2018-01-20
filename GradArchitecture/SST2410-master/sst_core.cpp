#include <sst/core/sst_config.h>
#include <sst_core/sst_core.hpp>
#include <sstream>
#include <sst/core/interfaces/simpleMem.h>
#include <sst/core/interfaces/stringEvent.h>
#include <sst/core/simulation.h>
#include <cstdlib>
#include <math.h>
#include <fstream>

#define print_request(req_to_print) \
{\
	std::cout<<\
	(req_to_print->cmd==Interfaces::SimpleMem::Request::Command::Write?"Write":\
	(req_to_print->cmd==Interfaces::SimpleMem::Request::Command::Read?"Read":\
	(req_to_print->cmd==Interfaces::SimpleMem::Request::Command::ReadResp?"ReadResp":\
	(req_to_print->cmd==Interfaces::SimpleMem::Request::Command::WriteResp?"WriteResp":"Some flush"\
))))<<std::endl<<\
"\tTo Address "<<req_to_print->addr<<std::endl<<\
"\tSize "<<req_to_print->size<<std::endl<<\
"\tId "<<req_to_print->id<<std::endl;\
}

namespace XSim
{
	namespace SST
	{
		int in_mul = 0;
		int in_div = 0;
		int in_ls = 0;
		int in_int = 0;


		struct node{
			int remaining_cycles;
			int memloc;
			int iready;
			int jready;
			int executing;
			int complete;
			int destreg;
			int read;
			int operation;
			node *ires;
			node *jres;
			node *next;
			node *prev;

		};
		typedef struct node *nodep;

		nodep beginning_of_int;
		nodep end_of_int;
		nodep reg_file[8];
		int int_empty = 1;
		int using_int = 0;

		nodep beginning_of_div;
		nodep end_of_div;
		int div_empty = 1;
		int using_div = 0;

		nodep beginning_of_mul;
		nodep end_of_mul;
		int mul_empty = 1;
		int using_mul = 0;

		nodep beginning_of_ls;
		int ls_empty = 1;
		int using_ls = 0;
		int to_dec_ls = 0;

		int16_t *int_arr;
		int16_t *mul_arr;
		int16_t *div_arr;
		int16_t ls_arr = 0;
		int regread = 0;


		nodep *int_used_arr;
		nodep *mul_used_arr;
		nodep *div_used_arr;

		int stalls = 0;



		core::core(ComponentId_t id, Params& params):
		Component(id)
		{
			//***********************************************************************************************
			//*	Some of the parameters may not be avaiable here, e.g. if --run-mode is "init"				*
			//*		As a consequence, this section shall not throw any errors. Instead use setup section	*
			//***********************************************************************************************

			// verbose is one of the configuration options for this component
			verbose = (uint32_t) params.find<uint32_t>("verbose", verbose);
			// clock_frequency is one of the configuration options for this component
			clock_frequency=params.find<std::string>("clock_frequency",clock_frequency);


			n_read_requests=params.find<uarch_t>("n_read_requests",n_read_requests);
			n_write_requests=params.find<uarch_t>("n_write_requests",n_write_requests);

			int_num=params.find<uint16_t>("integernum",int_num);
			int_res=params.find<uint16_t>("integerres",int_res);
			int_lat=params.find<uint16_t>("integerlat",int_lat);
			div_num=params.find<uint16_t>("dividernum",div_num);
			div_res=params.find<uint16_t>("dividerres",div_res);
			div_lat=params.find<uint16_t>("dividerlat",div_lat);
			mul_num=params.find<uint16_t>("multipliernum",mul_num);
			mul_res=params.find<uint16_t>("multiplierres",mul_res);
			mul_lat=params.find<uint16_t>("multiplierlat",mul_lat);
			ls_res=params.find<uint16_t>("lsres",ls_res);
			ls_num=params.find<uint16_t>("lsnum",ls_num);
			ls_lat=params.find<uint16_t>("lslat",ls_lat);

			program_file=params.find<std::string>("program",program_file);
			output_file=params.find<std::string>("output",output_file);



			// Create the SST output with the required verbosity level
			output = new Output("mips_core[@t:@l]: ", verbose, 0, Output::STDOUT);

			bool success;
			output->verbose(CALL_INFO, 1, 0, "Configuring cache connection...\n");

			//
			// The following code creates the SST interface that will connect to the memory hierarchy
			//
			data_memory_link = dynamic_cast<SimpleMem*>(Super::loadModuleWithComponent("memHierarchy.memInterface", this, params));
			SimpleMem::Handler<core> *data_handler=
			new SimpleMem::Handler<core>(this,&core::memory_callback);
			if(!data_memory_link)
			{
				output->fatal(CALL_INFO, -1, "Error loading memory interface module.\n");
			}
			else
			{
				output->verbose(CALL_INFO, 1, 0, "Loaded memory interface successfully.\n");
			}
			success=data_memory_link->initialize("data_memory_link", data_handler );
			if(success)
			{
				output->verbose(CALL_INFO, 1, 0, "Loaded memory initialize routine returned successfully.\n");
			}
			else
			{
				output->fatal(CALL_INFO, -1, "Failed to initialize interface: %s\n", "memHierarchy.memInterface");
			}
			output->verbose(CALL_INFO, 1, 0, "Configuration of memory interface completed.\n");

			// tell the simulator not to end without us
			registerAsPrimaryComponent();
			primaryComponentDoNotEndSim();
		}


		void core::setup()
		{
			output->output("Setting up.\n");
			Super::registerExit();

			// Create a tick function with the frequency specified
			Super::registerClock( clock_frequency, new Clock::Handler<core>(this, &core::tick ) );

			// Memory latency is used to make write/read requests to the SST simulated memory
			//  Simple wrapper to register callbacks
			memory_latency = new SSTMemory(data_memory_link);
			core::loadProgram();
			cycles=0;
			instructions=0;
		}


		void core::init(unsigned int phase)
		{
			output->output("Initializing.\n");
			//Nothing to do here
		}

		void core::loadProgram(){
			printf("%d\n",ls_res );
			printf("%d\n",ls_lat );

			programArray = (uint16_t*)malloc(65536*sizeof(uint16_t));
			data_memory = (int16_t*)malloc(65536*sizeof(int16_t));

			int_arr = (int16_t*)malloc(int_num*sizeof(int16_t));
			for(int i=0;i<int_num;i++)
			int_arr[i]=0;
			div_arr = (int16_t*)malloc(div_num*sizeof(int16_t));
			for(int i=0;i<div_num;i++)
			div_arr[i]=0;
			mul_arr = (int16_t*)malloc(mul_num*sizeof(int16_t));
			for(int i=0;i<mul_num;i++)
			mul_arr[i]=0;

			int_used_arr = (nodep*)malloc(int_num*sizeof(nodep));
			for(int i=0;i<int_num;i++)
			int_used_arr[i]=NULL;

			div_used_arr = (nodep*)malloc(div_num*sizeof(nodep));
			for(int i=0;i<div_num;i++)
			div_used_arr[i]=NULL;
			mul_used_arr = (nodep*)malloc(mul_num*sizeof(nodep));
			for(int i=0;i<mul_num;i++)
			mul_used_arr[i]=NULL;

			for(int i=0;i<8;i++)
			reg_file[i]=NULL;

			std::ifstream file(program_file);
			std::string str;
			while (std::getline(file, str))
			{
				if(str.front() != '#'){
					if(str.length() > 5){
						programArray[instr_count] = (int)strtol(str.substr(0,4).c_str(), NULL, 16);
						//printf("%d\n",programArray[instr_count]);
						data_memory[instr_count++] = (int)strtol(str.substr(5,8).c_str(), NULL, 16);
						//printf("%d\n",data_memory[instr_count-1]);
					}
					else
					programArray[instr_count++] = (int)strtol(str.c_str(), NULL, 16);
				}
			}
		}

		void core::finish()
		{

			FILE * pFile;
			pFile = fopen (output_file.c_str(),"w");
			fprintf(pFile,"{\"cycles\":%d,\n",cycles);
			fprintf(pFile,"\"integer\":[");

			for(int i=0;i<int_num;i++){
				fprintf(pFile,"{ \"id\" : %d, \"instructions\" : %d}",i,int_arr[i]);
				if(i+1<int_num)
				fprintf(pFile,",");
				else
				fprintf(pFile,"],\n");
			}

			fprintf(pFile,"\"multiplier\":[");

			for(int i=0;i<mul_num;i++){
				fprintf(pFile,"{ \"id\" : %d, \"instructions\" : %d}",i,mul_arr[i]);
				if(i+1<mul_num)
				fprintf(pFile,",");
				else
				fprintf(pFile,"],\n");
			}

			fprintf(pFile,"\"divider\":[");

			for(int i=0;i<div_num;i++){
				fprintf(pFile,"{ \"id\" : %d, \"instructions\" : %d}",i,div_arr[i]);
				if(i+1<div_num)
				fprintf(pFile,",");
				else
				fprintf(pFile,"],\n");
			}

			fprintf(pFile,"\"ls\":[");

			for(int i=0;i<ls_num;i++){
				fprintf(pFile,"{ \"id\" : %d, \"instructions\" : %d}",i,ls_arr);
				if(i+1<ls_num)
				fprintf(pFile,",");
				else
				fprintf(pFile,"],\n");
			}

			fprintf(pFile,"\"reg reads\" : %d,\n",regread);
			fprintf(pFile,"\"stalls\" : %d}",stalls);

			fclose(pFile);
		}

		bool core::tick(Cycle_t cycle)
		{
			printf("\n\nTick %d:\n", cycles);


			//***********************************************************************************************
			//*	What you need to do is to change the logic in this function with instruction execution *
			//***********************************************************************************************

			//std::cout<<"core::tick"<<std::endl;
			cycles++;


			std::function<void(uarch_t, uarch_t)> callback_function = [this](uarch_t request_id, uarch_t addr)
			{
				nodep current = beginning_of_ls;

				//Broadcast first to allow those waiting to immediately read
				current->complete = 1;
				to_dec_ls++;
				if(current->destreg > -1 && reg_file[current->destreg] == current)
				reg_file[current->destreg] = NULL;

				if(current->next != NULL)
				beginning_of_ls = current->next;

				else{
					beginning_of_ls = NULL;
					ls_empty = 1;
				}
				printf("Ls broadcasted\n");

				nodep current2 = beginning_of_int;
				while(current2 != NULL){
					if(current2->iready == 0){
						if(current2->ires->complete == 1){
							current2->iready = 1;
							current2->ires = NULL;
						}
					}
					if(current2->jready == 0){
						if(current2->jres->complete == 1){
							current2->jready = 1;
							current2->jres = NULL;
						}
					}

					if(current2->iready == 1 && current->jready == 1 && current2->executing == 0 && current2->read == 0){
						current2->read = 1;
						printf("Int Read instructions\n");
					}
					current2 = current2->next;
				}


				current2 = beginning_of_mul;
				while(current2 != NULL){
					if(current2->iready == 0){
						if(current2->ires->complete == 1){
							current2->iready = 1;
							current2->ires = NULL;
						}
					}
					if(current2->jready == 0){
						if(current2->jres->complete == 1){
							current2->jready = 1;
							current2->jres = NULL;
						}
					}
					if(current2->iready == 1 && current->jready == 1 && current2->executing == 0 && current2->read == 0){
						current2->read = 1;
						printf("Mul Read instructions\n");
					}
					current2 = current2->next;
				}

				current2 = beginning_of_div;
				while(current2 != NULL){
					if(current2->iready == 0){
						if(current2->ires->complete == 1){
							current2->iready = 1;
							current2->ires = NULL;
						}
					}
					if(current2->jready == 0){
						if(current2->jres->complete == 1){
							current2->jready = 1;
							current2->jres = NULL;
						}
					}
					if(current2->iready == 1 && current->jready == 1 && current2->executing == 0 && current2->read == 0){
						current2->read = 1;
						printf("Div Read instructions\n");
					}
					current2 = current2->next;
				}

				delete(current);

				if((instr_run == instr_count) && (int_empty == 1 || beginning_of_int == NULL) && (div_empty == 1 || beginning_of_div == NULL) && (mul_empty == 1 || beginning_of_mul == NULL) && (ls_empty == 1 || beginning_of_ls == NULL)){
					primaryComponentOKToEndSim();
					unregisterExit();
					return true;
				}
			};

			nodep current = beginning_of_int;
			nodep oldint = NULL;
			nodep oldintlast = NULL;

			int to_dec_int = 0;

			//Broadcast first to allow those waiting to immediately read
			while(current != NULL){
				nodep togo = current->next;

				if(current->remaining_cycles == 0 && current->executing == 1){
					current->complete = 1;
					to_dec_int++;
					if(current->destreg > -1 && reg_file[current->destreg] == current)
					reg_file[current->destreg] = NULL;

					if(current->prev != NULL && current->next != NULL){
						current->prev->next = current->next;
						current->next->prev = current->prev;
					}
					else if(current->prev == NULL && current-> next != NULL){
						beginning_of_int = current->next;
						beginning_of_int->prev = NULL;
					}
					else if(current->prev != NULL && current-> next == NULL){

						current->prev->next = NULL;
						end_of_int = current->prev;
					}
					else if(current->prev == NULL && current-> next == NULL){

						beginning_of_int = NULL;
						end_of_int = NULL;
						int_empty = 1;
					}
					if(oldint == NULL){
						oldint = current;
						oldintlast = current;
						current->next = NULL;
					}
					else{
						oldintlast->next = current;
						oldintlast = current;
						current->next = NULL;
					}
					printf("int broadcasted\n");
				}
				if(togo == NULL)
				break;
				else
				current = togo;

			}

			current = beginning_of_div;
			nodep olddiv = NULL;
			nodep olddivlast = NULL;

			int to_dec_div = 0;

			//Broadcast first to allow those waiting to immediately read
			while(current != NULL){
				nodep togo = current->next;
				if(current->remaining_cycles == 0 && current->executing == 1){
					current->complete = 1;
					to_dec_div++;
					if(current->destreg > -1 && reg_file[current->destreg] == current)
					reg_file[current->destreg] = NULL;

					if(current->prev != NULL && current->next != NULL){
						current->prev->next = current->next;
						current->next->prev = current->prev;
					}
					else if(current->prev == NULL && current-> next != NULL){
						beginning_of_div = current->next;
						beginning_of_div->prev = NULL;
					}
					else if(current->prev != NULL && current-> next == NULL){
						current->prev->next = NULL;
						end_of_div = current->prev;
					}
					else if(current->prev == NULL && current-> next == NULL){
						beginning_of_div = NULL;
						end_of_div = NULL;
						div_empty = 1;
					}

					if(olddiv == NULL){
						olddiv = current;
						olddivlast = current;
						current->next = NULL;
					}
					else{
						olddivlast->next = current;
						olddivlast = current;
						current->next = NULL;
					}

					printf("div broadcasted\n");
				}
				if(togo == NULL)
				break;
				else
				current = togo;
			}

			current = beginning_of_mul;
			nodep oldmul = NULL;
			nodep oldmullast = NULL;

			int to_dec_mul = 0;

			//Broadcast first to allow those waiting to immediately read
			while(current != NULL){
				nodep togo = current->next;
				if(current->remaining_cycles == 0 && current->executing == 1){
					current->complete = 1;
					to_dec_mul++;
					if(current->destreg > -1 && reg_file[current->destreg] == current)
					reg_file[current->destreg] = NULL;

					if(current->prev != NULL && current->next != NULL){
						current->prev->next = current->next;
						current->next->prev = current->prev;
					}
					else if(current->prev == NULL && current-> next != NULL){
						beginning_of_mul = current->next;
						beginning_of_mul->prev = NULL;
					}
					else if(current->prev != NULL && current-> next == NULL){
						current->prev->next = NULL;
						end_of_mul = current->prev;
					}
					else if(current->prev == NULL && current-> next == NULL){
						beginning_of_mul = NULL;
						end_of_mul = NULL;
						mul_empty = 1;
					}
					if(oldmul == NULL){
						oldmul = current;
						oldmullast = current;
						current->next = NULL;
					}
					else{
						oldmullast->next = current;
						oldmullast = current;
						current->next = NULL;
					}
					printf("mul broadcasted\n");
				}
				if(togo == NULL)
				break;
				else
				current = togo;
			}

			current = beginning_of_int;
			while(current != NULL){
				if(current->iready == 0){
					if(current->ires->complete == 1){
						current->iready = 1;
						current->ires = NULL;
					}
				}
				if(current->jready == 0){
					if(current->jres->complete == 1){
						current->jready = 1;
						current->jres = NULL;
					}
				}

				if(current->iready == 1 && current->jready == 1 && current->executing == 0 ){
					if(current->read == 1 && using_int < int_num){
						for(int i=0;i<int_num;i++){
							if(int_used_arr[i] == NULL){
								int_used_arr[i]=current;
								int_arr[i]++;
								break;
							}
						}
						current->executing = 1;
						printf("int executed 1st cycle\n");
						current->remaining_cycles-=1;
						using_int++;
					}
					else if(current->read == 0){
						current->read = 1;
						printf("int Read instructions\n");
					}
				}

				else if(current->executing == 1){
					current->remaining_cycles-=1;
					printf("int executed one cycle\n");
				}

				if(current->next == NULL)
				break;
				else
				current = current->next;
			}


			current = beginning_of_div;

			while(current != NULL){
				if(current->iready == 0){
					if(current->ires->complete == 1){
						current->iready = 1;
						current->ires = NULL;
					}
				}
				if(current->jready == 0){
					if(current->jres->complete == 1){
						current->jready = 1;
						current->jres = NULL;
					}
				}

				if(current->iready == 1 && current->jready == 1 && current->executing == 0 ){
					if(current->read == 1 && using_div < div_num){
						for(int i=0;i<div_num;i++){
							int found = 0;
							if(div_used_arr[i] == NULL){
								div_used_arr[i]=current;
								div_arr[i]++;
								found = 1;
								break;
							}
							if(found == 0)
							printf("NOT FOUND\n");
						}
						current->executing = 1;
						printf("div executed one cycle\n");
						current->remaining_cycles-=1;
						using_div++;
					}
					else if(current->read == 0){
						current->read = 1;
						printf("div Read instructions\n");
					}
				}

				else if(current->executing == 1){
					current->remaining_cycles-=1;
					printf("div executed one cycle\n");
				}

				if(current->next == NULL)
				break;
				else
				current = current->next;
			}

			current = beginning_of_mul;

			while(current != NULL){
				if(current->iready == 0){
					if(current->ires->complete == 1){
						current->iready = 1;
						current->ires = NULL;
					}
				}
				if(current->jready == 0){
					if(current->jres->complete == 1){
						current->jready = 1;
						current->jres = NULL;
					}
				}

				if(current->iready == 1 && current->jready == 1 && current->executing == 0 ){
					if(current->read == 1 && using_mul < mul_num){
						for(int i=0;i<mul_num;i++){
							if(mul_used_arr[i] == NULL){
								mul_used_arr[i]=current;
								mul_arr[i]++;
								break;
							}
						}
						current->executing = 1;
						printf("mul executed one cycle\n");
						current->remaining_cycles-=1;
						using_mul++;
					}
					else if(current->read == 0){
						current->read = 1;
						printf("mul Read instructions\n");
					}
				}

				else if(current->executing == 1){
					current->remaining_cycles-=1;
					printf("mul executed one cycle\n");
				}

				if(current->next == NULL)
				break;
				else
				current = current->next;
			}

			current = beginning_of_ls;

			while(current != NULL){
				if(current->iready == 0){
					if(current->ires->complete == 1){
						current->iready = 1;
						current->ires = NULL;
					}
				}
				if(current->jready == 0){
					if(current->jres->complete == 1){
						current->jready = 1;
						current->jres = NULL;
					}
				}

				if(current->iready == 1 && current->jready == 1 && current->executing == 0){
					if(current->read == 1 && using_ls < ls_num && current == beginning_of_ls){
						ls_arr++;
						current->executing = 1;
						printf("ls executed one cycle\n");
						current->remaining_cycles-=1;
						using_ls++;
					}
					else if(current->read == 0){
						current->read = 1;
						printf("ls Read instructions\n");
					}
				}

				else if(current->remaining_cycles == 0){
					if(current->operation == 0)
					memory_latency->read(current->memloc, callback_function);
					else
					memory_latency->write(current->memloc, callback_function);

					current->executing = 0;
				}
				else if(current->executing == 1){
					current->remaining_cycles-=1;
					printf("ls executed one cycle\n");
				}

				if(current->next == NULL)
				break;
				else
				current = current->next;
			}


			if((instr_run == instr_count) && (int_empty == 1 || beginning_of_int == NULL) && (div_empty == 1 || beginning_of_div == NULL) && (mul_empty == 1 || beginning_of_mul == NULL) && (ls_empty == 1 || beginning_of_ls == NULL)){
				primaryComponentOKToEndSim();
				unregisterExit();
				return true;
			}

			if(instr_run<instr_count){
				uint16_t instr = programArray[instr_run++];

				//add/sub/and/nor
				if((instr & 0xF800) == 0x0000 || (instr & 0xF800) == 0x0800 || (instr & 0xF800) == 0x1000 || (instr & 0xF800) == 0x1800){
					if(in_int < int_res){
						nodep next = new node;
						if(beginning_of_int == NULL){
							beginning_of_int = next;
							end_of_int = next;
							next->next = NULL;
							next->prev = NULL;
						}
						else{
							end_of_int->next = next;
							next->prev = end_of_int;
							next->next = NULL;
							end_of_int = next;
						}
						if(reg_file[instr>>5 & 0x0007] != NULL){
							next->ires = reg_file[instr>>5 & 0x0007];
							next->iready = 0;

						}
						else{
							next->iready = 1;
							regread++;

						}
						if(reg_file[instr>>2 & 0x0007] != NULL){
							next->jres = reg_file[instr>>2 & 0x0007];
							next->jready = 0;
						}
						else{
							regread++;
							next->jready = 1;

						}

						reg_file[instr>>8 & 0x0007] = next;
						next->remaining_cycles = int_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = (instr>>8 & 0x0007);
						int_empty = 0;
						in_int++;
						printf("Add/sub/and/nor issued\n");
					}
					else{
						instr_run--;
						stalls++;
					}
				}

				//Liz,lis,lui
				else if((instr & 0xF800) == 0x8000 || (instr & 0xF800) == 0x8800){
					if(in_int < int_res){
						nodep next = new node;
						if(beginning_of_int == NULL){
							beginning_of_int = next;
							end_of_int = next;
							next->next = NULL;
							next->prev = NULL;
						}
						else{
							end_of_int->next = next;
							next->prev = end_of_int;
							next->next = NULL;
							end_of_int = next;
						}
						next->iready = 1;
						next->jready = 1;

						reg_file[instr>>8 & 0x0007] = next;
						next->remaining_cycles = int_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = (instr>>8 & 0x0007);
						int_empty = 0;
						in_int++;
						printf("Lis/liz/lui issued\n");
					}
					else{
						instr_run--;
						stalls++;
					}
				}

				//lui
				else if((instr & 0xF800) == 0x9000){
					if(in_int < int_res){
						nodep next = new node;
						if(beginning_of_int == NULL){
							beginning_of_int = next;
							end_of_int = next;
							next->next = NULL;
							next->prev = NULL;
						}
						else{
							end_of_int->next = next;
							next->prev = end_of_int;
							next->next = NULL;
							end_of_int = next;
						}
						if(reg_file[instr>>8 & 0x0007] != NULL){
							next->ires = reg_file[instr>>8 & 0x0007];
							next->iready = 0;
						}
						else{
							next->iready = 1;
							regread++;
						}
						next->jready = 1;

						reg_file[instr>>8 & 0x0007] = next;
						next->remaining_cycles = int_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = (instr>>8 & 0x0007);
						int_empty = 0;
						in_int++;
						printf("Lis/liz/lui issued\n");
					}
					else{
						instr_run--;
						stalls++;
					}
				}



				//Halt
				else if((instr & 0xF800) == 0x6800){
					if(in_int < int_res){
						nodep next = new node;
						if(beginning_of_int == NULL){
							beginning_of_int = next;
							end_of_int = next;
							next->next = NULL;
							next->prev = NULL;
						}
						else{
							end_of_int->next = next;
							next->prev = end_of_int;
							next->next = NULL;
							end_of_int = next;
						}
						next->iready = 1;
						next->jready = 1;

						next->remaining_cycles = int_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = -1;
						int_empty = 0;
						in_int++;
						printf("Halt issued\n");
					}
					else{
						instr_run--;
						stalls++;
					}
				}


				//Put
				else if((instr & 0xF800) == 0x7000){
					if(in_int < int_res){
						nodep next = new node;
						if(beginning_of_int == NULL){
							beginning_of_int = next;
							end_of_int = next;
							next->next = NULL;
							next->prev = NULL;
						}
						else{
							end_of_int->next = next;
							next->prev = end_of_int;
							next->next = NULL;
							end_of_int = next;
						}

						if(reg_file[instr>>5 & 0x0007] != NULL){
							next->ires = reg_file[instr>>5 & 0x0007];
							next->iready = 0;
						}
						else{
							next->iready = 1;
							regread++;
						}

						next->jready = 1;

						next->remaining_cycles = int_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = -1;
						int_empty = 0;
						in_int++;
						printf("put issued\n");
					}
					else{
						instr_run--;
						stalls++;
					}
				}

				//Div/mod/exp instr
				else if((instr & 0xF800) == 0x2000 || (instr & 0xF800) == 0x3800 || (instr & 0xF800) == 0x3000){
					if(in_div < div_res){
						nodep next = new node;
						if(beginning_of_div == NULL){
							beginning_of_div = next;
							end_of_div = next;
							next->next = NULL;
							next->prev = NULL;
						}
						else{
							end_of_div->next = next;
							next->prev = end_of_div;
							next->next = NULL;
							end_of_div = next;
						}

						if(reg_file[instr>>5 & 0x0007] != NULL){
							next->ires = reg_file[instr>>5 & 0x0007];
							next->iready = 0;
						}
						else{
							next->iready = 1;
							regread++;
						}
						if(reg_file[instr>>2 & 0x0007] != NULL){
							next->jres = reg_file[instr>>2 & 0x0007];
							next->jready = 0;
						}
						else{
							next->jready = 1;
							regread++;
						}

						reg_file[instr>>8 & 0x0007] = next;
						next->remaining_cycles = div_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = (instr>>8 & 0x0007);
						div_empty = 0;
						in_div++;
						printf("div/mod/exp issued\n");
					}
					else{
						instr_run--;
						stalls++;
					}
				}

				//Mult instr
				else if((instr & 0xF800) == 0x2800){
					if(in_mul < mul_res){
						nodep next = new node;
						if(beginning_of_mul == NULL){
							beginning_of_mul = next;
							end_of_mul = next;
							next->next = NULL;
							next->prev = NULL;
						}
						else{
							end_of_mul->next = next;
							next->prev = end_of_mul;
							next->next = NULL;
							end_of_mul = next;
						}

						if(reg_file[instr>>5 & 0x0007] != NULL){
							next->ires = reg_file[instr>>5 & 0x0007];
							next->iready = 0;
						}
						else{
							next->iready = 1;
							regread++;
						}
						if(reg_file[instr>>2 & 0x0007] != NULL){
							next->jres = reg_file[instr>>2 & 0x0007];
							next->jready = 0;
						}
						else{
							next->jready = 1;
							regread++;
						}

						reg_file[instr>>8 & 0x0007] = next;
						next->remaining_cycles = mul_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = (instr>>8 & 0x0007);
						mul_empty = 0;
						in_mul++;
						printf("mul issued\n");
					}
					else{
						instr_run--;
						stalls++;
					}
				}

				//LW
				else if((instr & 0xF800) == 0x4000){

					if(in_ls < ls_res){
						nodep next = new node;
						if(beginning_of_ls == NULL){
							beginning_of_ls = next;
							next->next = NULL;
						}
						else{
							nodep current = beginning_of_ls;
							while(current->next != NULL)
							current = current->next;

							current->next = next;
							next->next = NULL;
						}


						if(reg_file[instr>>5 & 0x0007] != NULL){
							next->ires = reg_file[instr>>5 & 0x0007];
							next->iready = 0;
						}
						else{
							next->iready = 1;
							regread++;
						}
						next->jready = 1;

						reg_file[instr>>8 & 0x0007] = next;
						next->remaining_cycles = ls_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = (instr>>8 & 0x0007);
						next->memloc = data_memory[instr_run-1];
						next->operation = 0;
						ls_empty = 0;
						in_ls++;
						printf("lw issued\n");
					}
					else{
						instr_run--;
						stalls++;
					}

				}

				//SW
				else if((instr & 0xF800) == 0x4800){
					if(in_ls < ls_res){
						nodep next = new node;
						if(beginning_of_ls == NULL){
							beginning_of_ls = next;
							next->next = NULL;
						}
						else{
							nodep current = beginning_of_ls;
							while(current->next != NULL)
							current = current->next;

							current->next = next;
							next->next = NULL;
						}


						if(reg_file[instr>>2 & 0x0007] != NULL){
							next->ires = reg_file[instr>>2 & 0x0007];
							next->iready = 0;
						}
						else{
							next->iready = 1;
							regread++;
						}

						if(reg_file[instr>>5 & 0x0007] != NULL){
							next->jres = reg_file[instr>>5 & 0x0007];
							next->jready = 0;
						}
						else{
							next->jready = 1;
							regread++;
						}

						next->remaining_cycles = ls_lat;
						next->executing=0;
						next->complete=0;
						next->read = 0;
						next->destreg = -1;
						next->operation = 1;

						next->memloc = data_memory[instr_run-1];
						ls_empty = 0;
						in_ls++;
						printf("sw issued\n");

					}
					else{
						instr_run--;
						stalls++;
					}
				}
			}


			//Clean up
			in_int-=to_dec_int;
			using_int-=to_dec_int;

			in_div-=to_dec_div;
			using_div-=to_dec_div;

			in_mul-=to_dec_mul;
			using_mul-=to_dec_mul;

			in_ls-=to_dec_ls;
			using_ls-=to_dec_ls;

			to_dec_ls = 0;

			current = oldint;

			while(current != NULL){
				if(current->complete == 1){
					for(int i=0;i<int_num;i++){
						if(int_used_arr[i] == current){
							int_used_arr[i]=NULL;
							break;
						}
					}
					nodep temp = current->next;
					delete(current);
					current = temp;
					continue;
				}
				if(current->next != NULL)
					current = current->next;
					else
					break;
			}

			current = oldmul;
			while(current != NULL){
				if(current->complete == 1){
					for(int i=0;i<mul_num;i++){
						if(mul_used_arr[i] == current){
							mul_used_arr[i]=NULL;
							break;
						}
					}
					nodep temp = current->next;
					delete(current);
					current = temp;
					continue;
				}
				current = current->next;
			}

			current = olddiv;
			while(current != NULL){
				if(current->complete == 1){
					for(int i=0;i<div_num;i++){
						if(div_used_arr[i] == current){
							div_used_arr[i]=NULL;
							break;
						}
					}
					nodep temp = current->next;
					delete(current);
					current = temp;
					continue;
				}
				current = current->next;
			}
			return false;
		}


		void core::memory_callback(SimpleMem::Request *ev)
		{
			if(memory_latency)
			{
				memory_latency->callback(ev);

			}
		}

	}
}
