
import io.gatling.core.Predef._ // 2
import io.gatling.http.Predef._
//import scala.concurrent.duration._

class ABasicSimulation extends Simulation {

    //Exercise 1 define a simple scn without httpConf.

    //Exercise 2 define a simple scn with httpConf baseURL and http Request

    //Exercise 3 make a complex request wih pause different request...

    //use different injection mode (user at once, ramp ...)

    //Explain simulation

    //Exercise 4 use feeders

    //Exercise 5 define a new check (Default check response 20x or 304.)

    // Exercise 6 define an assertion (delay)

    val httpConf = http // 4
      .baseURL("http://localhost:8080") // 5 // Prepend all URLs
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
      .doNotTrackHeader("1")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

    val scn = scenario("BasicSimulation") // 7
      .exec(http("request_1")  // 8 // HTTP builder
              .get("/cities")) // 9 //My request
              .pause(5) // 10 // Other thing
      //
      //
      //.exec() //another http object

    setUp( // 11
      scn.inject(rampUsers(10000).over(100)) // 12
    ).protocols(httpConf) // 13
}
