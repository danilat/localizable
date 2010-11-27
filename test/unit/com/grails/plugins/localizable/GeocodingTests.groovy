package com.grails.plugins.localizable

import grails.test.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.JSON

//http://code.google.com/apis/maps/documentation/geocoding/
//Latitud="41.076769" Longitud="1.144041"
class GeocodingTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGMapsRequestByAddress() {
		def http = new HTTPBuilder( 'http://maps.googleapis.com' )
		http.get(path:'/maps/api/geocode/json', query : [ sensor:'false', address: 'San Esteban de Litera' ]){ resp, json ->
			assertEquals 200, resp.status
			assertEquals "OK", json.status
			assertTrue json.results.size() > 0
			assertNotNull json.results[0].geometry.location
		}
    }
	void testGMapsRequestByLatLng() {
		def http = new HTTPBuilder( 'http://maps.googleapis.com' )
		http.get(path:'/maps/api/geocode/json', query : [ sensor:'false', latlng: '41.9041359,0.3258535' ]){ resp, json ->
			assertEquals 200, resp.status
			assertEquals "OK", json.status
			assertTrue json.results.size() > 0
			assertTrue json.results[0].formatted_address.contains("San Esteban")
		}
    }
}
