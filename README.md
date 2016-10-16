# Hoan-coding-test

I did not have time to make a nice UI. Mostly it is a matter of margin and font size. For larger device I need to create different dims.xml to increase the spacing between views.

There are quite a few errors from the test pdf.
<ul>
<li> The unit is imperial so it should be mph instead of km/h </li>
<li> The forecast include the day of query in the forecast so to obtain 5 days one need to increase to 6 or 7 and eliminate the same day or the previous day as OpenWeatherMap include the previous day if query before 5AM and maybe later too. </li>
<li> The weather return by OpenWeatherMap during the early morning hour is completely wrong. The query http://api.openweathermap.org/data/2.5/weather?q=Atlanta,ga&units=imperial&appid=idhere return <br><br>
{"coord":{"lon":-84.39,"lat":33.75},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations","main":{"temp":45.32,"pressure":1004.52,"humidity":75,"temp_min":45.32,"temp_max":45.32,"sea_level":1038.48,"grnd_level":1004.52},"wind":{"speed":4.38,"deg":59.0019},"clouds":{"all":0},"dt":1476259842,"sys":{"message":0.1693,"country":"US","sunrise":1476272445,"sunset":1476313580},"id":4180439,"name":"Atlanta","cod":200} <br><br>
Notice that temp_min is equaled to temp_max </li>
</ul>
