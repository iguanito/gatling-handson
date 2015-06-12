
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ABasicSimulation extends Simulation {

  //Exercise 5 define a new check (Default check response 20x or 304.)

  val httpConf = http.baseURL("http://localhost:8080")
    .warmUp("http://localhost:8080/does_it_work.html")

  val beforeTravel = scenario("Finding my hotel in Atlanta")
    .exec(
      http("Find hotels in Atlanta")
        .get("/country/usa/city/atlanta/hotels")
    )
    .pause(10)
    .exec(
      http("Look at hotel DoubleTree in Atlanta")
        .get("/country/usa/city/atlanta/hotel/Doubletree"))
    .pause(10)
    .exec(
      http("Get reviews on this hotel")
        .get("/country/usa/city/atlanta/hotel/Doubletree/reviews")
        .check(status.is(200), headerRegex(HttpHeaderNames.ContentType,"application/json(.*)"), responseTimeInMillis lessThanOrEqual 200L)
    )

  val afterTravel = scenario("Post a review because it was a so good hotel")
  .exec(
    http("Post my review using a JSON body")
      .post("/country/usa/city/atlanta/hotel/Doubletree/reviews")
      .body(StringBody("""{"rating": "EXCELLENT","checkInDate": "2006-01-12","tripType": "FRIENDS","title": "GREAT","details": "GuENIAL"}""")).asJSON
    )

  setUp(beforeTravel.inject(atOnceUsers(10)).protocols(httpConf), afterTravel.inject(atOnceUsers(10)).protocols(httpConf))


  // Exercise 6 define an assertion (delay)


}
