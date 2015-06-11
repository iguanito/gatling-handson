
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ABasicSimulation extends Simulation {

  //Exercise 2 define a simple scn with httpConf baseURL and http Request
  //make 2 requests to our simulation
  val httpConf = http.baseURL("http://localhost:8080")
    .acceptCharsetHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .doNotTrackHeader("1")
    .disableWarmUp

  val scn = scenario("My Scenario")
    .exec(
      http("My check request")
        .get("/does_it_work.html") // Will actually make a request on "http://localhost:8080/does_it_work.html"
    )
    .exec(
      http("My other Request")
        .get("/country/usa/city/atlanta/hotels"))

  setUp(scn.inject(atOnceUsers(10)).protocols(httpConf))
  // see HttpProtocolHelper to get more configuration fields

  //Exercise 3 make a complex request wih pause different between requests...

  //use different injection mode (user at once, ramp ...)

  //Explain simulation

  //Exercise 4 use feeders

  //Exercise 5 define a new check (Default check response 20x or 304.)

  // Exercise 6 define an assertion (delay)


}
