// <![CDATA[
function initialize() {

    var mapOptions = {
        center: new google.maps.LatLng(1.348041667, 103.8198361),
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    //var map = google.maps.Map(document.getElementById('displayedMap'));
    map = PF('mapDisplay').getMap();
    var input = (document.getElementById('searchTextField'));
    var autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.bindTo('bounds', map);

    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({
        map: map
    });

    autocomplete.addListener('place_changed', function () {
        infowindow.close();
        marker.setVisible(false);
        input.className = '';
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            // Inform the user that the place was not found and return.
            input.className = 'notfound';
            return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
            //map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            // map.setZoom(17);  // Why 17? Because it looks good.
        }
        marker.setIcon(/** @type {google.maps.Icon} */({
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(35, 35)
        }));
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);

        var address = '';
        if (place.address_components) {
            address = [
                (place.address_components[0] && place.address_components[0].short_name || ''),
                (place.address_components[1] && place.address_components[1].short_name || ''),
                (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
        }

        infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
        infowindow.open(map, marker);

        alert(place.geometry.location) // FOR DEBUGGING PURPORSES
    });

    // Sets a listener on a radio button to change the filter type on Places
    // Autocomplete.
    function setupClickListener(id, types) {
        var radioButton = document.getElementById(id);
        google.maps.event.addDomListener(radioButton, 'click', function () {
            autocomplete.setTypes(types);
        });
    }

    setupClickListener('changetype-all', []);
    setupClickListener('changetype-establishment', ['establishment']);
    setupClickListener('changetype-geocode', ['geocode']);
}

google.maps.event.addDomListener(window, 'load', initialize);
// ]]>

function openSearch() {
    document.getElementById("bar").style.display = "none";
    document.getElementById("searchBar").style.display = "inline-block";
}
function closeSearch() {
    document.getElementById("bar").style.display = "inline-block";
    document.getElementById("searchBar").style.display = "none";
    return false;
}
function openFilter() {
    document.getElementById("bar").style.display = "none";
    document.getElementById("filterBar").style.display = "inline-block";
}
function closeFilter() {
    document.getElementById("bar").style.display = "inline-block";
    document.getElementById("filterBar").style.display = "none";
    return false;
}