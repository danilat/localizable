package com.grails.plugins.localizable

import grails.test.*

class GeocodingServiceTests extends GrailsUnitTestCase {
	def geocodingService
    protected void setUp() {
		geocodingService = new GeocodingService();
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFindPointsByAddress() {
		def points = geocodingService.findPointsByAddress("San Esteban de Litera")
		assertTrue points.size() > 0
    }

	void testFindPointsByAddressWithZeroResults() {
		def points = geocodingService.findPointsByAddress("atomarporelculomacho")
		assertEquals 0, points.size()
    }

	void testFindLatLngsByAddress() {
		def latLngs = geocodingService.findLatLngsByAddress("San Esteban de Litera")
		assertNotNull latLngs[0].lat
		assertNotNull latLngs[0].lng
    }

	void testFindLatLngByAddress() {
		def latLng = geocodingService.findLatLngByAddress("San Esteban de Litera")
		assertNotNull latLng.lat
		assertNotNull latLng.lng
    }

	void testFindPointsByLatLng() {
		def points = geocodingService.findPointsByLatLng(41.9041359, 0.3258535)
		assertTrue points.size() > 0
    }

	void testFindPointsByLatLngWithZeroResults() {
		def points = geocodingService.findPointsByLatLng("", "")
		assertEquals 0, points.size()
    }

	void testFindAddressesByLatLng() {
		def addresses = geocodingService.findAddressesByLatLng(41.9041359, 0.3258535)
		assertTrue addresses.size() > 0
    }

	void testFindAddressByLatLng() {
		def address = geocodingService.findAddressByLatLng(41.9041359, 0.3258535)
		assertNotNull address
    }
}
