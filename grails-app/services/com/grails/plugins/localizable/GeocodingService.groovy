package com.grails.plugins.localizable

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.JSON

class GeocodingService {

    static transactional = false
	def http = new HTTPBuilder('http://maps.googleapis.com')
	
	def findLatLngByAddress(address){
		def latLngs = findLatLngsByAddress(address)
		if(latLngs.size()>0)
			return latLngs[0]
	}

	def findLatLngsByAddress(address){
		def points = findPointsByAddress(address)
		def latLngs = []
		points.each{ point -> 
			point.geometry.location.latitude
			latLngs << [lat: point.geometry.location.lat, 
						lng: point.geometry.location.lng]
		}
		return latLngs
	}
	
	def findPointsByAddress(address) {
		return findPoints([ sensor:'false', address: address ])
    }

	def findAddressByLatLng(lat, lng){
		def address = findAddressesByLatLng(lat, lng)
		if(address.size()>0)
			return address[0]
	}

	def findAddressesByLatLng(lat, lng){
		def points = findPointsByLatLng(lat, lng)
		def addresses = []
		points.each{ point ->
			addresses << point.formatted_address
		}
		return addresses
	}
	
	def findPointsByLatLng(lat, lng) {
		return findPoints([ sensor:'false', latlng: "${lat},${lng}" ])
    }

	private def findPoints(query){
		def points = []
		http.get(path:'/maps/api/geocode/json', query : query){ resp, json ->
			if(resp.status != 200)
				throw new RuntimeException("Unexpected failure: ${resp.status}")
			if(json.status != "OK" && json.status != "ZERO_RESULTS")
				throw new RuntimeException("Googlemaps API error: ${json.status}")
			points = json.results
		}
		return points
	}
}
