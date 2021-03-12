package org.emoflon.grapel.benchmark.flights.karthesian;

import java.util.LinkedList;
import java.util.List;

import org.emoflon.grapel.benchmark.EvalContainer;
import org.emoflon.grapel.benchmark.EvalElement;
import org.emoflon.grapel.benchmark.PatternMatchingEngine;

import FlightGrapeLLoadApama.grapel.Flightapama.FlightapamaGrapeLAPI;
import FlightGrapeLLoadEMoflon.grapel.Flightemoflon.FlightemoflonGrapeLAPI;

public class FlightKarthesianBenchmark {
	private static final long sleepTime = 10;
	protected EvalElement runApamaLoad(String modelInstanceFileName, int expectedEvents, PatternMatchingEngine engine) {
		FlightApamaKarthesianMonitor monitor = new FlightApamaKarthesianMonitor();		
		FlightapamaGrapeLAPI api = monitor.init(modelInstanceFileName, engine);
		
		long tic = System.currentTimeMillis();
		api.update();
		while (expectedEvents > api.getConnectedFlightsEventHandler().getAllEvents().size())
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		long toc = System.currentTimeMillis();
		int eventNumber =  api.getConnectedFlightsEventHandler().getAllEvents().size();
		
		monitor.shutdown();
		
		return new EvalElement(toc-tic, eventNumber);		
	}
	protected EvalElement runEmoflonLoad(String modelInstanceFileName, int expectedEvents, PatternMatchingEngine engine) {
		FlightEMoflonKarthesianMonitor monitor = new FlightEMoflonKarthesianMonitor();		
		FlightemoflonGrapeLAPI api = monitor.init(modelInstanceFileName, engine);
		
		long tic = System.currentTimeMillis();
		api.update();
		while (expectedEvents > api.getConnectedFlightsEventHandler().getAllEvents().size())
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		long toc = System.currentTimeMillis();
		int eventNumber =  api.getConnectedFlightsEventHandler().getAllEvents().size();
		
		monitor.shutdown();
		
		return new EvalElement(toc-tic, eventNumber);		
	}
	protected EvalContainer runLoad(String modelInstanceFileName, int expectedEvents, PatternMatchingEngine engine, int numberOfRuns) {
		EvalContainer container = new EvalContainer(modelInstanceFileName, expectedEvents, engine);
		System.out.println("[!] RUNNING" + engine.toString());
		for(int i=0; i<numberOfRuns;i++) container.apamaLoad.add(runApamaLoad(modelInstanceFileName, expectedEvents, engine));
		for(int i=0; i<numberOfRuns;i++) container.emoflonLoad.add(runEmoflonLoad(modelInstanceFileName, expectedEvents, engine));
		
		return container;
	}
	protected List<EvalContainer> runLoad(String modelInstanceFileName, int expectedEvents, int numberOfRuns) {
		List<EvalContainer> eval = new LinkedList<EvalContainer>();
		eval.add(runLoad(modelInstanceFileName, expectedEvents, PatternMatchingEngine.HiPE, numberOfRuns));
		eval.add(runLoad(modelInstanceFileName, expectedEvents, PatternMatchingEngine.Viatra, numberOfRuns));
//		eval.add(runLoad(modelInstanceFileName, expectedEvents, PatternMatchingEngine.Democles, numberOfRuns));
		
		return eval;
	}
//	public static String countNodes(String modelInstanceFileName) {
//		FlightEMoflonKarthesianMonitor monitor = new FlightEMoflonKarthesianMonitor();		
//		FlightemoflonGrapeLAPI api = monitor.init(modelInstanceFileName, PatternMatchingEngine.HiPE);
//		
//		ModelEventHandler modelHandler = api.getModelEventHandler();
//		LinkedList<FlightModel> models = new LinkedList<FlightModel>();
//		
//		api.update();
//		modelHandler.getAllEvents().forEach(event -> models.add(event.getModel()));
//		FlightModel model = models.get(0);
//		
//		int numAirports = model.getAirports().getAirports().size();
//		int numGates = 0;
//		for(Airport airport: model.getAirports().getAirports())
//			numGates += airport.getGates().size(); 
//		int numRoutes = model.getRoutes().getRoutes().size();
//		int numPlanes = model.getPlanes().getPlanes().size();
//		int numPersons = model.getPersons().getPersons().size();
//		int numBookings = model.getBookings().getBookings().size();
//		int numTravels = 0;
//		for(Booking booking: model.getBookings().getBookings())
//			numTravels += booking.getTravels().size();
//		int numFlights = model.getFlights().getFlights().size();
//		
//		// add flightModel and other top containers => + 7
//		long totalNodes = numAirports + numGates + numRoutes + numPlanes + numPersons + numBookings +
//				numTravels + numFlights + 7;
//		
//		monitor.shutdown();
//		
//		return modelInstanceFileName + ": #Airports = " + numAirports + ", #Gates = " + numGates + ", #Routes = "
//			+ numRoutes + ", #Planes = " + numPlanes + ", #Persons = " + numPersons + ", #Bookings = " + numBookings
//			+ ", #Travels =" + numTravels + ", #Flights = " + numFlights + ", #total = " + totalNodes;			
//	}
	
	public static void main (String[] arg) {
//		List<String> output = new LinkedList<String>();
//		String[] instancesToCount = {"test294","test663","test1179","test1843","test2652","test3611"};
//		
//		for(String instance:instancesToCount) output.add(countNodes(instance+".xmi"));
//		for(String s: output) System.out.println(s);
		
//		FlightKarthesianBenchmark benchmark = new FlightKarthesianBenchmark();
//		
//		String instance = "test_1179";
//		int expectedEvents = 1179;
//		List<EvalContainer> eval = benchmark.runLoad(instance +".xmi", expectedEvents, 10);
//		for(EvalContainer container: eval) container.printElements();
//		EvalElement emoflonLoad = benchmark.runEmoflonLoad(instance + ".xmi", expectedEvents, PatternMatchingEngine.HiPE);
//		EvalElement apamaLoad = benchmark.runApamaLoad(instance +".xmi", expectedEvents, PatternMatchingEngine.Democles);
		
		
//		System.out.println("Benchmark with load on Apama on " + instance + " with " + expectedEvents + " expected events and got " 
//			+ apamaLoad.eventNumber + " events took " + apamaLoad.time + " ms");
//		System.out.println("Benchmark with load on EMoflon on " + instance + " with " + expectedEvents + " expected events and got "
//			+ emoflonLoad.eventNumber + " events took " + emoflonLoad.time + " ms");
		
	}
}
