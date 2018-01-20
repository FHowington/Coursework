#include <sst_core/sst_core.hpp>
#include <sst/core/element.h>


// Named as create_<component name>
static SST::Component* create_core(SST::ComponentId_t id,
										SST::Params& params)
{
	return new XSim::SST::core( id, params );
}




/** ***************************************************************************
 *
 *			This structure specifies the links in an SST component
 *				Description available in sst/core/elements.h
 *
 ** ***************************************************************************/
static const SST::ElementInfoPort core_ports[] = {
	{ "data_memory_link", "Link to the data memHierarchy", NULL },
	{ NULL, NULL, NULL }
};

/** ***************************************************************************
 *
 *			This structure specifies the parameters of an SST component
 *				Description available in sst/core/elements.h
 *
 ** ***************************************************************************/
static const SST::ElementInfoParam core_params[] = {
	{ "verbose", "Verbosity for debugging. Increased numbers for increased verbosity.", "0" },
	{ "clock_frequency", "Sets the clock of the core in Hz", "0"} ,
	{ "n_read_requests", "Number of memory read requests", "1"} ,
	{ NULL, NULL, NULL }
};

/** ***************************************************************************
 *
 *			This structure specifies the components of the SST element
 *				Description available in sst/core/elements.h
 *
 ** ***************************************************************************/
static const SST::ElementInfoComponent components[] = {
	{
		"core",
		"Description of mips_core",
		nullptr,
		create_core,
		core_params,
		core_ports,
		COMPONENT_CATEGORY_PROCESSOR,
		nullptr
	},
	{ nullptr, nullptr, nullptr, nullptr, nullptr, nullptr }
};


/** ***************************************************************************
 *
 *			This structure describes this library
 *				Description available in sst/core/elements.h
 *
 ** ***************************************************************************/
extern "C"
{

SST::ElementLibraryInfo XSim_eli = {
	"XSim",
	"Simple MIPS simulator",
	components,
	nullptr, // Events
	nullptr, // Introspectors
	nullptr, // Modules
	nullptr, // Subcomponents
	nullptr, // partitioners
	nullptr, // python module generators
	nullptr, // generators
};
}
