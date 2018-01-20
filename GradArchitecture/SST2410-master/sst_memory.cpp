#include <sst_core/sst_memory.hpp>


namespace XSim
{
namespace SST
{

SSTMemory::SSTMemory(SimpleMem *data_memory_link):
	data_memory_link(data_memory_link)
{

}
void SSTMemory::callback(SimpleMem::Request *ev)
{
	try{
		callbacks.at(ev->id)(ev->id,ev->addr);
		callbacks.erase(ev->id);
	}
	catch(std::exception &e)
	{
		std::cout<<"No such callback"<<std::endl;
		exit(EXIT_FAILURE);
	}
}

void SSTMemory::read(uarch_t address, std::function<void(uarch_t, uarch_t)> cb)
{
	SimpleMem::Request *req=new SimpleMem::Request(
				//Command
				SimpleMem::Request::Command::Read,
				//uint64 addr
				address,
				//uint64 size
				sizeof(uarch_t),
				//flags_t
				0,
				//flags_t
				0
				);
	callbacks.insert({req->id, cb});
	data_memory_link->sendRequest(req);
}

void SSTMemory::write(uarch_t address, std::function<void(uarch_t, uarch_t)> cb)
{
	SimpleMem::Request *req=new SimpleMem::Request(
				//Command
				SimpleMem::Request::Command::Write,
				//uint64 addr
				address,
				//uint64 size
				sizeof(uarch_t)*2,
				//flags_t
				0,
				//flags_t
				0
				);
	callbacks.insert({req->id, cb});
	data_memory_link->sendRequest(req);
}

}
}
