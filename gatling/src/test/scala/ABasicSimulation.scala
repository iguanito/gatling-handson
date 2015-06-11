
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ABasicSimulation extends Simulation {

    //Exercise 1 define a simple scn without httpConf then inject 10 at once.
    val myscenario = scenario("First scenario").exec(http("request to check page").get("http://localhost:8080/does_it_work.html"))

    // inject trafic
    setUp(myscenario.inject(atOnceUsers(10)))
    //setUp(myscenario inject atOnceUsers(10))

    //Exercise 2 define a simple scn with httpConf baseURL and http Request

    //Exercise 3 make a complex request wih pause different request...

    //use different injection mode (user at once, ramp ...)

    //Explain simulation

    //Exercise 4 use feeders

    //Exercise 5 define a new check (Default check response 20x or 304.)

    // Exercise 6 define an assertion (delay)



}
