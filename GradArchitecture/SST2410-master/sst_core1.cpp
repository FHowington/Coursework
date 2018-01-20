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

	add_lat=params.find<uint16_t>("add",add_lat);
	sub_lat=params.find<uint16_t>("sub",sub_lat);
	and_lat=params.find<uint16_t>("and",and_lat);
	nor_lat=params.find<uint16_t>("nor",nor_lat);
	div_lat=params.find<uint16_t>("div",div_lat);
	mul_lat=params.find<uint16_t>("mul",mul_lat);
	mod_lat=params.find<uint16_t>("mod",mod_lat);
	exp_lat=params.find<uint16_t>("exp",exp_lat);

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
	for(int i=0; i<8; i++){
		registers[i]=0;
	for(int i=0; i<22; i++){
		counts[i]=0;
	}
	cycles=0;
	instructions=0;
	}
}


void core::init(unsigned int phase)
{
	output->output("Initializing.\n");
	//Nothing to do here
}

void core::loadProgram(){
	programArray = (uint16_t*)malloc(64000*sizeof(uint16_t));
	data_memory = (int16_t*)malloc(65536*sizeof(int16_t));


  std::ifstream file(program_file);
	    std::string str;
	    while (std::getline(file, str))
	    {
				if(str.front() != '#')
					programArray[instr_count++] = (int)strtol(str.c_str(), NULL, 16);
	    }
}

void core::finish()
{
	FILE * pFile;
  pFile = fopen (output_file.c_str(),"w");
	fprintf(pFile,"{\"registers\":[\n\t{\"r0\":%d,\"r1\":%d,\"r2\":%d,\"r3\":%d,\n\t \"r4\":%d,\"r5\":%d,\"r6\":%d,\"r7\":%d\n\t}],",registers[0],registers[1],registers[2],registers[3],registers[4],registers[5],registers[6],registers[7]);
	fprintf(pFile,"\n\"stats\":[\n\t{\"add\":%d,\"sub\":%d,\"and\":%d,\n",counts[0],counts[1],counts[2]);
	fprintf(pFile,"\t \"nor\":%d,\"div\":%d,\"mul\":%d,\n",counts[3],counts[4],counts[5]);
	fprintf(pFile,"\t \"mod\":%d,\"exp\":%d,\"lw\":%d,\n",counts[6],counts[7],counts[8]);
	fprintf(pFile,"\t \"sw\":%d,\"liz\":%d,\"lis\":%d,\n",counts[9],counts[10],counts[11]);
	fprintf(pFile,"\t \"lui\":%d,\"bp\":%d,\"bn\":%d,\n",counts[12],counts[13],counts[14]);
	fprintf(pFile,"\t \"bx\":%d,\"bz\":%d,\"jr\":%d,\n",counts[15],counts[16],counts[17]);
	fprintf(pFile,"\t \"jalr\":%d,\"j\":%d,\"halt\":%d,\n",counts[18],counts[19],counts[20]);
	fprintf(pFile,"\t \"put\":%d,\n",counts[21]);
	fprintf(pFile,"\t \"instructions\":%d,\n",instructions);
	fprintf(pFile,"\t \"cycles\":%d\n",cycles);
	fprintf(pFile,"\t}]\n}");
	fclose(pFile);
}

bool core::tick(Cycle_t cycle)
{

	//***********************************************************************************************
	//*	What you need to do is to change the logic in this function with instruction execution *
	//***********************************************************************************************

	//std::cout<<"core::tick"<<std::endl;
	cycles++;
	if(!busy)
	{
		if(instr_run<instr_count){
			instructions++;
			uint16_t instr = programArray[instr_run++];
			//Add instruction
			if((instr & 0xF800) == 0x0000){
				printf("%04X\n",instr);
				counts[0]++;
				wait_time = add_lat--;
				if(wait_time>0)
					busy=true;
				registers[instr>>8 & 0x0007] = registers[instr>>5 & 0x0007] + registers[instr>>2 & 0x0007];
			}

			//Subtract instruction
			else if((instr & 0xF800) == 0x0800){
				printf("%04X\n",instr);

				counts[1]++;
				wait_time = sub_lat--;
				if(wait_time>0)
					busy=true;
				registers[instr>>8 & 0x0007] = registers[instr>>5 & 0x0007] - registers[instr>>2 & 0x0007];
			}

			//And instr
			else if((instr & 0xF800) == 0x1000){
				printf("%04X\n",instr);

				counts[2]++;

				wait_time = and_lat--;
				if(wait_time>0)
					busy=true;
				registers[instr>>8 & 0x0007] = (registers[instr>>5 & 0x0007] & registers[instr>>2 & 0x0007]);
			}

			//Nor instr
			else if((instr & 0xF800) == 0x1800){
				printf("%04X\n",instr);

				counts[3]++;

				wait_time = nor_lat--;
				if(wait_time>0)
					busy=true;
				registers[instr>>8 & 0x0007] = ~(registers[instr>>5 & 0x0007] | registers[instr>>2 & 0x0007]);
			}

			//Div instr
			else if((instr & 0xF800) == 0x2000){
				printf("%04X\n",instr);

				counts[4]++;

				wait_time = div_lat--;
				if(wait_time>0)
					busy=true;
				registers[instr>>8 & 0x0007] = registers[instr>>5 & 0x0007] / registers[instr>>2 & 0x0007];
			}

			//Mult instr
			else if((instr & 0xF800) == 0x2800){
				printf("%04X\n",instr);

				counts[5]++;

				wait_time = mul_lat--;
				if(wait_time>0)
					busy=true;
				registers[instr>>8 & 0x0007] = (registers[instr>>5 & 0x0007] * registers[instr>>2 & 0x0007]) & 0xFFFF;
			}

			//Mod instr
			else if((instr & 0xF800) == 0x3000){
				printf("%04X\n",instr);

				counts[6]++;

				wait_time = mod_lat--;
				if(wait_time>0)
					busy=true;
				registers[instr>>8 & 0x0007] = registers[instr>>5 & 0x0007] % registers[instr>>2 & 0x0007];
			}

			//exp instr
			else if((instr & 0xF800) == 0x3800){
				printf("%04X\n",instr);

				counts[7]++;

				wait_time = exp_lat--;
				if(wait_time>0)
					busy=true;
				registers[instr>>8 & 0x0007] = ((int)pow(registers[instr>>5 & 0x0007], registers[instr>>2 & 0x0007])) & 0xFFFF;
			}

			//LW
			else if((instr & 0xF800) == 0x4000){
				printf("%04X %04X\n",instr,registers[instr>>5 & 0x0007]);
				counts[8]++;

						std::function<void(uarch_t, uarch_t)> callback_function = [this](uarch_t request_id, uarch_t addr)
				{
					this->busy=false;
				};
				memory_latency->read(std::rand()%1000, callback_function);
				registers[instr>>8 & 0x0007] = data_memory[registers[instr>>5 & 0x0007]];
				busy=true;
			}

			//SW
			else if((instr & 0xF800) == 0x4800){
				printf("%04X %04X\n",instr,registers[instr>>2 & 0x0007]);

				counts[9]++;

						std::function<void(uarch_t, uarch_t)> callback_function = [this](uarch_t request_id, uarch_t addr)
				{
					this->busy=false;
				};
				memory_latency->read(std::rand()%1000, callback_function);
				data_memory[registers[instr>>5 & 0x0007]] = registers[instr>>2 & 0x0007];
				busy=true;
			}

			else if((instr & 0xF800) == 0x8000){
				counts[10]++;
				printf("%04X\n",instr);

				registers[instr >> 8 & 0x0007] = (instr & 0x00FF);
			}

			else if((instr & 0xF800) == 0x8800){
				counts[11]++;
				printf("%04X\n",instr);
				registers[instr >> 8 & 0x0007] = ((instr & 0x0080) == 0x0080) ? (0xFF00 | (instr & 0x00FF)) : (instr & 0x00FF);
			}

			else if((instr & 0xF800) == 0x9000){
				counts[12]++;
				printf("%04X\n",instr);

				registers[instr >> 8 & 0x0007] = ((instr & 0x00FF)<<8) | (registers[instr >> 8 & 0x0007] & 0x00FF);
			}

			else if((instr & 0xF800) == 0xA000){
				counts[13]++;

				if(registers[instr >> 8 & 0x0007] > 0)
					instr_run = instr & 0x007F;
			}

			else if((instr & 0xF800) == 0xA800){
				counts[14]++;

				if(registers[instr >> 8 & 0x0007] < 0)
					instr_run = instr & 0x007F;
			}

			else if((instr & 0xF800) == 0xB000){
				counts[15]++;

				if(registers[instr >> 8 & 0x0007] != 0)
					instr_run = instr & 0x007F;
			}

			else if((instr & 0xF800) == 0xB800){
				counts[16]++;

				if(registers[instr >> 8 & 0x0007] == 0)
					instr_run = instr & 0x007F;
			}

			else if((instr & 0xF800) == 0x6000){
				counts[17]++;

				instr_run = (registers[instr >> 5 & 0x0007])>>1;
			}

			else if((instr & 0xF800) == 0x9800){
				counts[18]++;

				registers[instr >> 8 & 0x0007] = instr_run*2;
				instr_run = (registers[instr >> 5 & 0x0007])>>1;
			}

			else if((instr & 0xF800) == 0xC000){
				counts[19]++;
				instr_run = (instr & 0x07FF) | (--instr_run & 0xF800);
			}

			else if((instr & 0xF800) == 0x6800){
				counts[20]++;
				printf("%04X\n",instr);

				primaryComponentOKToEndSim();
				unregisterExit();
				return true;
			}

			else if((instr & 0xF800) == 0x7000){
				counts[21]++;
				printf("%04X\n",instr);
				//printf("%d\n",registers[instr >> 5 & 0x0007]);
			}
		}
		else{
			// Finish simulation
			primaryComponentOKToEndSim();
			unregisterExit();
			return true;
		}
	}
	else{
		wait_time--;
		if(wait_time==1)
			busy=false;
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
