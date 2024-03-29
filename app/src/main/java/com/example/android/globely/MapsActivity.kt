package com.example.android.globely

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.example.android.globely.databinding.MapActivityBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.geojson.GeoJsonLayer
import com.google.maps.android.geojson.GeoJsonPolygonStyle
import org.json.JSONArray
import org.json.JSONObject


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var gameDone: Boolean = false
    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapActivityBinding
    private var countriesOnMap: MutableList<Int> = mutableListOf()
    lateinit var jsonString: String
    lateinit var jsonArray: JSONArray
    lateinit var jsonObject: JSONObject
    private val countries: List<String> = listOf(
        "argentina",
        "armenia",
        "australia",
        "austria",
        "azerbaijan",
        "bahamas",
        "bahrain",
        "bangladesh",
        "barbados",
        "belarus",
        "belgium",
        "belize",
        "benin",
        "bhutan",
        "bolivia",
        "bosnia and herzegovina",
        "botswana",
        "brazil",
        "brunei",
        "bulgaria",
        "burkina faso",
        "burundi",
        "canada",
        "cabo verde",
        "cambodia",
        "cameroon",
        "central african republic",
        "chad",
        "chile",
        "china",
        "colombia",
        "comoros",
        "congo",
        "costa rica",
        "cote d'Ivoire",
        "cuba",
        "croatia",
        "cyprus",
        "czech republic",
        "denmark",
        "djibouti",
        "dominica",
        "dominican republic",
        "east timor",
        "ecuador",
        "egypt",
        "el salvador",
        "equatorial guinea",
        "eritrea",
        "estonia",
        "eswatini",
        "ethiopia",
        "finland",
        "france",
        "gabon",
        "gambia",
        "georgia",
        "germany",
        "ghana",
        "greece",
        "grenada",
        "guatemala",
        "guinea",
        "guinea-bissau",
        "guyana",
        "haiti",
        "honduras",
        "hungary",
        "iceland",
        "india",
        "indonesia",
        "iran",
        "iraq",
        "ireland",
        "israel",
        "italy",
        "jamaica",
        "japan",
        "jordan",
        "kazakhstan",
        "kenya",
        "kiribati",
        "kosovo",
        "kuwait",
        "kyrgyzstan",
        "laos",
        "latvia",
        "lebanon",
        "lesotho",
        "liberia",
        "libya",
        "liechtenstein",
        "lithuania",
        "luxembourg",
        "macedonia",
        "madagascar",
        "malawi",
        "malaysia",
        "maldives",
        "mali",
        "malta",
        "mauritania",
        "mauritius",
        "mexico",
        "micronesia",
        "moldova",
        "monaco",
        "mongolia",
        "montenegro",
        "morocco",
        "mozambique",
        "myanmar",
        "namibia",
        "nepal",
        "netherlands",
        "new zealand",
        "nicaragua",
        "niger",
        "nigeria",
        "niue",
        "north korea",
        "norway",
        "oman",
        "pakistan",
        "palau",
        "palestine",
        "panama",
        "papua new guinea",
        "paraguay",
        "peru",
        "philippines",
        "poland",
        "portugal",
        "qatar",
        "romania",
        "russia",
        "rwanda",
        "saint kitts and nevis",
        "saint lucia",
        "saint vincent and the grenadines",
        "samoa",
        "san marino",
        "sao tome and principe",
        "saudi arabia",
        "senegal",
        "serbia",
        "seychelles",
        "sierra leone",
        "singapore",
        "slovakia",
        "slovenia",
        "solomon islands",
        "somalia",
        "south africa",
        "south korea",
        "south sudan",
        "spain",
        "sri lanka",
        "sudan",
        "suriname",
        "sweden",
        "switzerland",
        "syria",
        "taiwan",
        "tajikistan",
        "tanzania",
        "thailand",
        "togo",
        "tonga",
        "trinidad and tobago",
        "tunisia",
        "turkey",
        "turkmenistan",
        "uganda",
        "ukraine",
        "united arab emirates",
        "united kingdom",
        "united states",
        "uruguay",
        "uzbekistan",
        "vanuatu",
        "vatican city",
        "venezuela",
        "vietnam",
        "western sahara",
        "yemen",
        "zambia",
        "zimbabwe",
        "afganistan",
        "albania",
        "algeria",
        "andorra",
        "angola",
        "antigua and barbuda")
    private val coordinatesOfCountries: Map<String, LatLng> = mapOf(
        "canada" to LatLng(56.4428473594893, -102.76011627882923),
        "portugal" to LatLng(40.10118442734383, -7.8721837198595620),
        "spain" to LatLng(40.18351849779206, -3.384500958570459),
        "andorra" to LatLng(42.55245662922965, 1.6006361364955313),
        "france" to LatLng(46.80878115773712, 2.595144100657739),
        "argentina" to LatLng(-35.05684173083449, -64.86434273949061),
        "armenia" to LatLng(40.26743318406566, 44.846368454503896),
        "australia" to LatLng(-25.217806859075168, 136.68885152776065),
        "austria" to LatLng(47.56236537649463, 14.42041915855767),
        "azerbaijan" to LatLng(40.47218052291189, 47.79527954891865),
        "bahamas" to LatLng(24.527151179508202, -78.01949165185658),
        "bahrain" to LatLng(26.0781088903926, 50.54894556778629),
        "bangladesh" to LatLng(23.484432631456063, 90.09464222663802),
        "barbados" to LatLng(13.138548626418036, -59.553155573369004),
        "belarus" to LatLng(53.76801290446372, 28.182769500757775),
        "belgium" to LatLng(50.79281643514409, 4.986163865301685),
        "belize" to LatLng(17.03142967283723, -88.74098487130598),
        "benin" to LatLng(10.361863496881206, 2.6226686647872164),
        "bhutan" to LatLng(27.282736003020396, 90.4778142961709),
        "bolivia"  to LatLng(-17.478304837050157, -64.09017874750388),
        "bosnia and herzegovina"  to LatLng(44.291611306508706, 17.60232503172705),
        "botswana"  to LatLng(-21.23619170804137, 23.567472901647086),
        "brazil"  to LatLng(-8.2094679301514, -52.40912746293984),
        "brunei"  to LatLng(4.538316974640404, 114.69678170152866),
        "bulgaria"  to LatLng(42.91090515419249, 25.728485995270624),
        "burkina faso"  to LatLng(12.357978880914773, -1.9427894686986122),
        "burundi"  to LatLng(-3.1566030845872053, 29.932682872622777),
        "cabo verde" to LatLng(15.969280083271352, -23.99204045361528),
        "cambodia" to LatLng(13.181304332514294, 105.09647161895919),
        "cameroon" to LatLng(5.211154507588032, 12.910855649601153),
        "central african republic" to LatLng(6.976818285190465, 20.70451268997437),
        "chad" to LatLng(14.769424668913967, 19.422012164343332),
        "chile" to LatLng(-23.53557558045065, -68.96394968046012),
        "china" to LatLng(35.377728770950846, 105.3113239119147),
        "colombia" to LatLng(4.19525059446272, -73.2633508983399),
        "comoros" to LatLng(-11.943837855460131, 43.89096770313374),
        "congo" to LatLng(-0.509810628931018, 15.808927153646817),
        "costa rica" to LatLng(10.302975096146122, -83.52588721199572),
        "côte d'Ivoire" to LatLng(7.4623251406563815, -5.763697263764241),
        "cuba" to LatLng(21.671188702801015, -78.50736888356644),
        "croatia" to LatLng(45.897808561768116, 16.000939465688365),
        "cyprus" to LatLng(35.094451389085926, 33.313805622093426),
        "czech republic" to LatLng(49.664038188413166, 15.340437394651598),
        "denmark" to LatLng(55.960893255524624, 9.41578188292696),
        "djibouti" to LatLng(12.084024762288001, 42.51402001027573),
        "dominica" to LatLng(15.442188790112485, -61.3320642718087),
        "dominican republic" to LatLng(19.002277598462122, -70.08371804701572),
        "east timor" to LatLng(-8.751581569903436, 125.78560971192353),
        "ecuador" to LatLng(-1.4795550921506493, -78.40690219732308),
        "egypt" to LatLng(27.07126632321091, 28.671445836285887),
        "el salvador" to LatLng(13.83491332374069, -88.61509576333694),
        "equatorial guinea" to LatLng(1.6237365191310411, 10.401201684402007),
        "eritrea" to LatLng(16.028229074049335, 38.80274120065437),
        "estonia" to LatLng(58.70849514845337, 25.881618801522155),
        "eswatini" to LatLng(-26.582093404705287, 31.445616796803318),
        "ethiopia" to LatLng(8.555902185298066, 40.187945159517696),
        "finland" to LatLng(64.0458170783012, 27.176298622404538),
        "france" to LatLng(47.112403302939825, 2.35327386302948),
        "gabon" to LatLng(-0.29698431786821206, 11.827318674256551),
        "gambia" to LatLng(13.592608155705902, -15.296096463269468),
        "georgia" to LatLng(42.2035314726912, 43.52011903080953),
        "germany" to LatLng(50.997283399308216, 10.264592666384004),
        "ghana" to LatLng(8.293891822459747, -1.2226106851533278),
        "greece" to LatLng(39.57377312355167, 21.830510410424488),
        "grenada" to LatLng(12.243132707296883, -61.60697344844933),
        "guatemala" to LatLng(15.698216820131238, -90.40405463001386),
        "guinea" to LatLng(10.677307873030031, -10.88343997590157),
        "guinea-bissau" to LatLng(12.138921618740303, -14.865793635540937),
        "guyana" to LatLng(5.081630227657435, -58.662002395073266),
        "haiti" to LatLng(19.052176845336568, -72.63933213388013),
        "honduras" to LatLng(15.052734647731324, -86.70338163109318),
        "hungary" to LatLng(47.045150478898925, 19.650058372457362),
        "iceland" to LatLng(64.86207106293769, -18.21218229554594),
        "india" to LatLng(23.14903269370035, 78.4177228996289),
        "indonesia" to LatLng(-3.5641187487963406, 121.79310131523839),
        "iran" to LatLng(32.29558597863721, 54.785071953323914),
        "iraq" to LatLng(33.346880007093645, 43.262446930225444),
        "ireland" to LatLng(52.659616018337516, -7.497875543602852),
        "israel" to LatLng(31.703687875717577, 34.919880827763876),
        "italy" to LatLng(43.11270003153561, 12.619753901685655),
        "jamaica" to LatLng(18.16731947471982, -77.37950903061801),
        "japan" to LatLng(36.39373803275172, 138.83008365369813),
        "jordan" to LatLng(31.521016382070815, 36.51771695175807),
        "kazakhstan" to LatLng(48.557725503300915, 67.9171996300062),
        "kenya" to LatLng(0.34077193275979517, 37.77026183952738),
        "kiribati" to LatLng(-3.3545278868139805, -168.71058587489094),
        "kosovo" to LatLng(42.61581392306145, 20.947746373669116),
        "kuwait" to LatLng(29.458063017565745, 47.49992006589669),
        "kyrgyzstan" to LatLng(41.406405732308926, 74.8650357433629),
        "laos" to LatLng(19.039656926386904, 103.91707066836459),
        "latvia" to LatLng(56.68225858418951, 25.90409750501814),
        "lebanon" to LatLng(34.00818734845127, 35.778901738294635),
        "lesotho" to LatLng(-29.279465042611807, 28.46321253383528),
        "liberia" to LatLng(6.127242412683317, -9.319271122448852),
        "libya" to LatLng(27.59952574082535, 17.881852791071886),
        "liechtenstein" to LatLng(47.13852943550827, 9.567336075861702),
        "lithuania" to LatLng(55.419280949551315, 23.821812649896525),
        "luxembourg" to LatLng(49.72222087146566, 6.12172060891322),
        "macedonia" to LatLng(41.4668822265859, 21.70695582742944),
        "madagascar" to LatLng(-20.31196474829354, 47.16609341745012),
        "malawi" to LatLng(-12.930752563810524, 34.14780221750649),
        "malaysia" to LatLng(4.2956964893834035, 102.15166428674263),
        "maldives" to LatLng(1.0256839344759754, 73.40204258190505),
        "mali" to LatLng(18.823659763934266, -2.4787949232246813),
        "malta" to LatLng(35.88381863245769, 14.464066930637646),
        "mauritania" to LatLng(20.802656811653367, -10.62086302240907),
        "mauritius" to LatLng(-20.252558999455506, 57.638979854955004),
        "mexico" to LatLng(23.910821686183414, -103.3261128694326),
        "micronesia" to LatLng(7.4222800156196564, 150.52457826002268),
        "moldova" to LatLng(47.489217493177506, 28.757908035802892),
        "monaco" to LatLng(43.73833662359715, 7.419782981722855),
        "mongolia" to LatLng(47.43353936514834, 104.62738512873288),
        "montenegro" to LatLng(42.89307103251385, 19.18878099560104),
        "morocco" to LatLng(32.388725280368966, -5.257072415775164),
        "mozambique" to LatLng(-17.33184871079692, 36.53432964994032),
        "myanmar" to LatLng(22.011071954812415, 96.57595235005752),
        "namibia" to LatLng(-22.616123099248743, 15.704251485188337),
        "nepal" to LatLng(28.2020765853979, 84.08004203364774),
        "netherlands" to LatLng(52.147159278855085, 5.567178649582775),
        "new zealand" to LatLng(-42.904231228031605, 170.80377984887738),
        "nicaragua" to LatLng(13.099604697867639, -84.65886121390744),
        "niger" to LatLng(16.837102969618517, 8.79728592226642),
        "nigeria" to LatLng(10.002352772551506, 8.445723420538076),
        "niue" to LatLng(-19.05518253145292, -169.86362402936123),
        "north korea" to LatLng(40.22693901369924, 126.91738698416647),
        "norway" to LatLng(61.44045109784618, 9.155024976844308),
        "oman" to LatLng(20.25910777465593, 56.17100616955627),
        "pakistan" to LatLng(29.708228621690278, 69.35875520428083),
        "palau" to LatLng(7.518195485931089, 134.4995795964987),
        "palestine" to LatLng(32.14567524157309, 35.120086192264),
        "panama" to LatLng(8.492254862510592, -80.32176140909951),
        "papua new guinea" to LatLng(-6.29778369653055, 145.20277928177163),
        "paraguay" to LatLng(-22.64885320938576, -58.29449027038155),
        "peru" to LatLng(-9.444498149646101, -74.99319858047635),
        "philippines" to LatLng(12.544602595219002, 122.99001715077449),
        "poland" to LatLng(52.06377303930631, 18.72937803078781),
        "portugal" to LatLng(39.85278024802654, -8.182157868606776),
        "qatar" to LatLng(25.334899386369695, 51.211531316094344),
        "romania" to LatLng(46.07176921542963, 24.625966289531412),
        "russia" to LatLng(64.17875913901506, 93.40657312589671),
        "rwanda" to LatLng(-1.9860857389073734, 29.887777266986443),
        "saint kitts and nevis" to LatLng(17.311765795548908, -62.7489746506744),
        "saint lucia" to LatLng(13.89403009833211, -60.951337547095996),
        "saint vincent and the grenadines" to LatLng(13.25720261583525, -61.19021059216122),
        "samoa" to LatLng(-13.645604782771045, -172.38105762525817),
        "san marino" to LatLng(43.94130914330877, 12.469011082944633),
        "sao tome and principe" to LatLng(0.26078519404537015, 6.594328669450277),
        "saudi arabia" to LatLng(23.654487438616723, 44.82057721716265),
        "senegal" to LatLng(14.73178847663638, -14.244138971524563),
        "serbia" to LatLng(44.04313541528453, 20.749142377506637),
        "seychelles" to LatLng(-4.651315815217715, 55.477062542186594),
        "sierra leone" to LatLng(8.614016165077798, -11.832055728921338),
        "singapore" to LatLng(1.3496331177385663, 103.84679606821392),
        "slovakia" to LatLng(48.74880839286979, 19.489627716567362),
        "slovenia" to LatLng(46.24345817737799, 14.59679861843281),
        "solomon islands" to LatLng(-8.819415416940187, 159.6707148004439),
        "somalia" to LatLng(2.857909300805719, 45.036497232805345),
        "south africa" to LatLng(-30.031320000477837, 24.85177021467843),
        "south korea" to LatLng(36.67843636055723, 128.047672818111),
        "south sudan" to LatLng(7.185858152678489, 30.945972817437713),
        "spain" to LatLng(39.99651988888133, -3.0053007594691494),
        "sri lanka" to LatLng(7.594262303626609, 80.73360259176181),
        "sudan" to LatLng(16.203611489108678, 30.864741553438268),
        "suriname" to LatLng(4.102725281054127, -55.825821172245526),
        "sweden" to LatLng(63.25034644999636, 16.573800381785414),
        "switzerland" to LatLng(46.86924631041533, 8.405950178291805),
        "syria" to LatLng(34.97416167726256, 38.75355164365015),
        "taiwan" to LatLng(23.650742859058735, 120.89990619663772),
        "tajikistan" to LatLng(38.71812921730594, 70.82032661615953),
        "tanzania" to LatLng(-6.659564728320991, 35.27080658591151),
        "thailand" to LatLng(15.819627196203452, 101.14599841999595),
        "togo" to LatLng(8.51896423199862, 0.9617413424661605),
        "tonga" to LatLng(-20.45221745224186, -174.75245136301746),
        "trinidad and tobago" to LatLng(10.440278821280302, -61.20424943865933),
        "tunisia" to LatLng(33.88286830193445, 9.92261631075464),
        "turkey" to LatLng(39.22213110796668, 35.050965270186005),
        "turkmenistan" to LatLng(39.28133473628666, 58.70108587834581),
        "uganda" to LatLng(1.6147046941458065, 32.127919511677256),
        "ukraine" to LatLng(49.136940518056846, 30.400304625321223),
        "united arab emirates" to LatLng(23.905200359509692, 54.40513290963034),
        "united kingdom" to LatLng(52.719414327961466, -1.550055943745214),
        "united states" to LatLng(39.82410537079313, -100.97597134274125),
        "uruguay" to LatLng(-33.246468323052596, -56.01010045359807),
        "uzbekistan" to LatLng(42.13109397718277, 63.65698617607261),
        "vanuatu" to LatLng(-15.778261059039178, 167.6937417749072),
        "vatican city" to LatLng(41.90355949574216, 12.453262690593625),
        "venezuela" to LatLng(7.278183895237846, -65.54154783811765),
        "vietnam" to LatLng(13.969445683857693, 108.30265628075529),
        "western sahara" to LatLng(24.69870649859547, -13.686817166216027),
        "yemen" to LatLng(16.004052368643006, 48.43262253652577),
        "zambia" to LatLng(-13.432500308429434, 27.562000357615567),
        "zimbabwe" to LatLng(-18.50057774951149, 29.407703491689393),
        "afganistan" to LatLng(33.2608753088689, 65.31162180146791),
        "albania" to LatLng(41.12732055162962, 20.1116657624197),
        "algeria" to LatLng(27.80152544309867, 3.270651350173375),
        "andorra" to LatLng(42.55567782502294, 1.6142668719792554),
        "angola" to LatLng(-12.375375893548672, 16.680768258019338),
        "antigua and barbuda" to LatLng(17.170638520402743, -61.79109658413467),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        jsonString = applicationContext.assets.readFile("countrycoordJSONOBJ.json")
        jsonArray = JSONArray(jsonString)
        jsonObject = jsonArray[0] as JSONObject

        supportActionBar?.hide()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val easyMode = intent.extras?.getSerializable("easy mode") as Boolean
        try {
            if (easyMode){
                val success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map_style_easy_mode
                    )
                )
            }
            else{
                val success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map_style_hard_mode
                    )
                )
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("MapsActivityRaw", "Can't find style.", e)
        }
        game()
    }

    @SuppressLint("Range")
    private fun game(){
        var solutionCountry = countries.random().toString()

        val ibSearch = findViewById<ImageButton>(R.id.ibSearch)
        val adapter = ArrayAdapter(
            this,
            R.layout.autocomplete_dropdown, R.id.tvDropdown, countries
        )
        val edInputField = findViewById<AutoCompleteTextView>(R.id.edInputField)
        edInputField.setAdapter(adapter)

        Log.i("kurac", "here")
        edInputField.onItemClickListener =
            OnItemClickListener onItemClickListener@{ parent, view, position, id ->

                edInputField.hideKeyboard()

                val inputCountry = adapter.getItem(position).toString()
                val idOfCountry = resources.getIdentifier(inputCountry.replace(" ", "_").replace("-", "_"), "raw", packageName)

                if (idOfCountry in countriesOnMap){
                    Toast.makeText(applicationContext, "Country already on the map", Toast.LENGTH_SHORT).show()
                }
                if (idOfCountry == 0){
                    Toast.makeText(applicationContext, "Invalid country", Toast.LENGTH_SHORT).show()
                    return@onItemClickListener
                }
                if (solutionCountry == inputCountry){
                    val cameraPositionOnCountry = coordinatesOfCountries[inputCountry]!!
                    Toast.makeText(applicationContext, "Correct!", Toast.LENGTH_SHORT).show()

                    edInputField.setText("")
                    val newCountryLayer = GeoJsonLayer(mMap, idOfCountry, applicationContext)
                    val style: GeoJsonPolygonStyle = newCountryLayer.defaultPolygonStyle
                    style.fillColor = Color.parseColor("#FF00ff00")
                    style.strokeColor = Color.parseColor("#FF00ff00")
                    style.strokeWidth = 1f

                    newCountryLayer.addLayerToMap()

                    val cameraPosition = CameraPosition.Builder()
                        .target(cameraPositionOnCountry)
                        .zoom(4f)
                        .build()

                    val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
                    mMap.animateCamera(cu)

                    ibSearch.setImageResource(R.drawable.outline_replay_24)
                    gameDone = true
                } else {
                    edInputField.setText("")

                    val cameraPositionOnCountry = coordinatesOfCountries[inputCountry]!!

                    val distanceBetweenCountries = getDistanceBetween(inputCountry, solutionCountry)

                    var colorFactor = distanceBetweenCountries / 5700
                    if (colorFactor > 1){
                        colorFactor = 1f
                    }
                    val newCountryLayer = GeoJsonLayer(mMap, idOfCountry, applicationContext)
                    val style: GeoJsonPolygonStyle = newCountryLayer.defaultPolygonStyle
                    if (distanceBetweenCountries < 60) {
                        style.fillColor = Color.parseColor("#bb10ff00")
                        style.strokeColor = Color.parseColor("#bb10ff00")
                        style.strokeWidth = 1f
                    } else{
                        style.fillColor = ColorUtils.blendARGB(Color.parseColor("#bb42ff00"), Color.parseColor("#bbff0000"), colorFactor)
                        style.strokeColor = ColorUtils.blendARGB(Color.parseColor("#bb42ff00"), Color.parseColor("#bbff0000"), colorFactor)
                        style.strokeWidth = 1f
                    }
                    newCountryLayer.addLayerToMap()

                    val cameraPosition = CameraPosition.Builder()
                        .target(cameraPositionOnCountry)
                        .zoom(4f)
                        .build()
                    val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
                    mMap.animateCamera(cu)

                    countriesOnMap.add(idOfCountry)
                }
            }
        ibSearch.setOnClickListener {
            if (gameDone){
                solutionCountry = countries.random().toString()
                ibSearch.setImageResource(R.drawable.outline_search_24)
                gameDone = false
                countriesOnMap.clear()
                mMap.clear()
                Toast.makeText(applicationContext, "New game started", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            edInputField.hideKeyboard()

            val inputCountry = edInputField.text.toString()
            val id = resources.getIdentifier(inputCountry.replace(" ", "_").replace("-", "_"), "raw", packageName)

            if (id in countriesOnMap){
                Toast.makeText(applicationContext, "Country already on the map", Toast.LENGTH_SHORT).show()
            }
            if (id == 0){
                Toast.makeText(applicationContext, "Invalid country", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (solutionCountry == inputCountry){
                val cameraPositionOnCountry = coordinatesOfCountries[inputCountry]!!
                Toast.makeText(applicationContext, "Correct!", Toast.LENGTH_SHORT).show()

                edInputField.setText("")
                val newCountryLayer = GeoJsonLayer(mMap, id, applicationContext)
                val style: GeoJsonPolygonStyle = newCountryLayer.defaultPolygonStyle
                style.fillColor = Color.parseColor("#FF00ff00")
                style.strokeColor = Color.parseColor("#FF00ff00")
                style.strokeWidth = 1f

                newCountryLayer.addLayerToMap()

                val cameraPosition = CameraPosition.Builder()
                    .target(cameraPositionOnCountry)
                    .zoom(4f)
                    .build()
                val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
                mMap.animateCamera(cu)

                ibSearch.setImageResource(R.drawable.outline_replay_24)
                gameDone = true
            }
            else {
                edInputField.setText("")

                val cameraPositionOnCountry = coordinatesOfCountries[inputCountry]!!

                val distanceBetweenCountries = getDistanceBetween(inputCountry, solutionCountry)

                var colorFactor = distanceBetweenCountries / 5700
                if (colorFactor > 1){
                    colorFactor = 1f
                }
                val newCountryLayer = GeoJsonLayer(mMap, id, applicationContext)
                val style: GeoJsonPolygonStyle = newCountryLayer.defaultPolygonStyle
                if (distanceBetweenCountries < 60) {
                    style.fillColor = Color.parseColor("#bb10ff00")
                    style.strokeColor = Color.parseColor("#bb10ff00")
                    style.strokeWidth = 1f
                } else{
                    style.fillColor = ColorUtils.blendARGB(Color.parseColor("#bb42ff00"), Color.parseColor("#bbff0000"), colorFactor)
                    style.strokeColor = ColorUtils.blendARGB(Color.parseColor("#bb42ff00"), Color.parseColor("#bbff0000"), colorFactor)
                    style.strokeWidth = 1f
                }
                newCountryLayer.addLayerToMap()

                val cameraPosition = CameraPosition.Builder()
                    .target(cameraPositionOnCountry)
                    .zoom(4f)
                    .build()
                val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
                mMap.animateCamera(cu)

                countriesOnMap.add(id)
            }
        }
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun AssetManager.readFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use { it.readText() }

    fun getDistanceBetween(inputCountry: String, solutionCountry: String): Float{
        val inputCountryCoords = jsonObject.get(inputCountry) as JSONArray
        val solutionCountryCoords = jsonObject.get(solutionCountry) as JSONArray

        val distanceBetweenCountries = FloatArray(1)
        var minDistance = 21000.0f
        for (i in 1..inputCountryCoords.length()){
            val inputCountryCoord = inputCountryCoords[i-1] as JSONArray
            for (j in 1..solutionCountryCoords.length()){
                val solutionCountryCoord = solutionCountryCoords[j-1] as JSONArray

                Location.distanceBetween(
                    solutionCountryCoord[0] as Double, solutionCountryCoord[1] as Double,
                    inputCountryCoord[0] as Double, inputCountryCoord[1] as Double,
                    distanceBetweenCountries)

                val distanceBetweenCountriesInKm = distanceBetweenCountries[0] / 1000
                if (distanceBetweenCountriesInKm < minDistance){
                    minDistance = distanceBetweenCountriesInKm
                }
            }
        }
        return  minDistance
    }
}