/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.data.jpa.web;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sample.data.jpa.domain.City;
import sample.data.jpa.domain.Hotel;
import sample.data.jpa.domain.HotelSummary;
import sample.data.jpa.domain.Review;
import sample.data.jpa.domain.ReviewDetails;
import sample.data.jpa.service.CitySearchCriteria;
import sample.data.jpa.service.CityService;
import sample.data.jpa.service.HotelService;

@RestController
public class MainController {

    @Autowired
    private CityService cityService;

    @Autowired
    private HotelService hotelService;

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public Page<City> findCities(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size) {
        PageRequest pageRequest = buildPageRequest(page, size);
        CitySearchCriteria criteria = new CitySearchCriteria(name);
        return cityService.findCities(criteria, pageRequest);
    }

    @RequestMapping(value = "/country/{countryName}/city/{cityName}/hotels", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public Page<HotelSummary> geHotelsInCity(@PathVariable String cityName,
                                             @PathVariable String countryName,
                                             @RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer size) {
        PageRequest pageRequest = buildPageRequest(page, size);
        City city = getCity(cityName, countryName);
        return cityService.getHotels(city, pageRequest);
    }

    @RequestMapping(value = "/country/{countryName}/city/{cityName}/hotel/{hotelName}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public Hotel getHotel(@PathVariable String cityName,
                          @PathVariable String countryName,
                          @PathVariable String hotelName) {
        City city = getCity(cityName, countryName);
        return getHotel(city, hotelName);
    }

    @RequestMapping(value = "/country/{countryName}/city/{cityName}/hotel/{hotelName}/reviews", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public Page<Review> getHotelReviews(@PathVariable String cityName,
                                        @PathVariable String countryName,
                                        @PathVariable String hotelName,
                                        @RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer size) {
        PageRequest pageRequest = buildPageRequest(page, size);
        City city = getCity(cityName, countryName);
        Hotel hotel = getHotel(city, hotelName);

        sleepRandomlyForMillis(300);

        return hotelService.getReviews(hotel, pageRequest);
    }

    private void sleepRandomlyForMillis(int milliseconds) {
        int randomInt = new Random().nextInt(5);
        if(randomInt == 4){
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/country/{countryName}/city/{cityName}/hotel/{hotelName}/reviews", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public void addHotelReview(@PathVariable String cityName,
                                        @PathVariable String countryName,
                                        @PathVariable String hotelName,
                                        @RequestBody ReviewDetails review) {
        City city = getCity(cityName, countryName);
        Hotel hotel = getHotel(city, hotelName);
        hotelService.addReview(hotel, review);

    }

    private Hotel getHotel(City city, String hotelName) {
        return hotelService
                .getHotel(city, hotelName)
                .orElseThrow(() -> new RuntimeException("MESSAGE: can't find hotel " + hotelName));
    }

    private City getCity(String cityName, String countryName) {
        return cityService
                .getCity(cityName, countryName)
                .orElseThrow(() -> new RuntimeException("MESSAGE: can't find city " + cityName));
    }

    private PageRequest buildPageRequest(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        PageRequest pageRequest = null;
        if (page != null && size != null) {
            pageRequest = new PageRequest(page, size);
        }
        return pageRequest;
    }

}