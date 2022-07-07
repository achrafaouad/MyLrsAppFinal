


package com.example.MyLrsAppFinal;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.MyLrsAppFinal.Models.*;
import com.example.MyLrsAppFinal.service.LrsService;
import com.example.MyLrsAppFinal.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/LrsEvent" ,method = { RequestMethod.GET, RequestMethod.POST })
public class Controller implements ErrorController {


    private final LrsService lrsService;
    private final UserService userService;



    @Autowired
    public Controller(LrsService lrsService, UserService userService) {
        this.lrsService = lrsService;
        this.userService = userService;
    }

    @PostMapping("/getEventpByName")
    public ResponseEntity<?> getEventParams(@RequestBody String EventName) {

        List<Event_params> tt = this.lrsService.getParams(EventName);

        return new ResponseEntity<>(tt,HttpStatus.OK);
    }


   @GetMapping("/getEvenParams")
    public ResponseEntity<?> getEventParams() {

        List<Event_params> tt = this.lrsService.getAllParams();

        return new ResponseEntity<>(tt,HttpStatus.OK);
    }

    @GetMapping("/getRouteNames")
    public ResponseEntity<?> getRouteNames() {

        List<String> tt = this.lrsService.getRouteNames();

        return new ResponseEntity<>(tt,HttpStatus.OK);
    }


    @PostMapping("/getEventypepByID")
    public ResponseEntity<?> getEventParamsById(@RequestBody Long id) {

        Optional<EventType> tt = this.lrsService.findEvtpById(id);

        return new ResponseEntity<>(tt.orElseThrow(),HttpStatus.OK);
    }

    @GetMapping("/getEventypes")
    public ResponseEntity<?> getEventParamsById() {

        List<EventType> tt = this.lrsService.findEvts();

        return new ResponseEntity<>(tt,HttpStatus.OK);
    }



    @PostMapping("/addEventsToRoute")
    public ResponseEntity<?> addExistantEventoRoutes(@RequestBody List<LineEventFromFront> Le) throws JsonProcessingException {

        System.out.println(Le);
        Integer ver = this.lrsService.verifyEventLine(Le);

        if (ver == 0) {
            this.lrsService.processEventExistLine(Le);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("please verify the Line Number" + ver);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/addPointEventsToRoute")
    public ResponseEntity<?> addPointEventsToRoute(@RequestBody List<PointEventFromFront> Le) throws JsonProcessingException {

        System.out.println(Le);
        Integer ver = this.lrsService.verifyEventPoint(Le);

        if (ver == 0) {
            this.lrsService.addPointEvent(Le);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("please verify the Line Number" + ver);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

    }



    @PutMapping("/updateSumbologyInfo")
    public ResponseEntity<?> updateSumbologyInfo(@RequestBody Symbology Le) {
        System.out.println(Le);
        Symbology ls = this.lrsService.updateSymbology(Le);
        Gson g = new Gson();
        LineStyle s = g.fromJson(ls.getSymbology(), LineStyle.class);
        System.out.println(s);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PutMapping("/updatecolorEvent")
    public ResponseEntity<?> onUpdateStyleColors(@RequestBody LineColorFomFront Le) {
        System.out.println(Le);
        System.out.println("hna fenn");


        this.lrsService.updateColors(Le);

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping("/updatecolorPointEvent")
    public ResponseEntity<?> onUpdatecolorPointEvent(@RequestBody LineColorFomFront Le) {
        System.out.println(Le);
        this.lrsService.updatePointColors(Le);
        return new ResponseEntity<>( HttpStatus.OK);
    }



    @PostMapping("/addEventType")
    public ResponseEntity<?> addEventType(@RequestBody String Le) {
        System.out.println(Le);
        Long id = this.lrsService.addEventTypeService(Le);

        return new ResponseEntity<>( id, HttpStatus.OK);
    }

    @PostMapping("/addEventType2")
    public ResponseEntity<?> addEventType2(@RequestBody String Le) {
        System.out.println(Le);
        Long id = this.lrsService.addEventTypeService2(Le);

        return new ResponseEntity<>( id, HttpStatus.OK);
    }


    @PostMapping("/deletethAtAll")
    public ResponseEntity<?> deletethAtAll(@RequestBody Long Le) {

        System.out.println(Le);
        System.out.println("deleting");
        Long id = this.lrsService.deletethAtAll(Le);

        return new ResponseEntity<>( id, HttpStatus.OK);
    }


    @PostMapping("/saveParams")
    public ResponseEntity<?> saveParams(@RequestBody SaveParams Le) {
        System.out.println(Le);
        this.lrsService.addEventParams(Le);

        return new ResponseEntity<>( HttpStatus.OK);
    }


    @PostMapping("/getStyleById")
    public ResponseEntity<?> getStyleById(@RequestBody Long id) {
        System.out.println(id);
        Symbology ver = this.lrsService.getStyleByid(id).orElseThrow(() -> new IllegalStateException("user not Found"));
        System.out.println(id);
        Gson g = new Gson();

        if (id == 10) {
            EtatStyle s = g.fromJson(ver.getSymbology(), EtatStyle.class);
            System.out.println(s);
            return new ResponseEntity<>(s, HttpStatus.OK);
        } else if (id == 21) {
            AccidentStyle s = g.fromJson(ver.getSymbology(), AccidentStyle.class);
            System.out.println(s);
            return new ResponseEntity<>(s, HttpStatus.OK);
        } else {
            LineStyle s = g.fromJson(ver.getSymbology(), LineStyle.class);
            System.out.println(s);
            return new ResponseEntity<>(s, HttpStatus.OK);
        }


    }


    @PostMapping("previewLineResult")
    public List<String> previewLineResult(@RequestBody Linear_Event le){
//     return this.lrsService.getGeojson();
        System.out.println(le);
        return this.lrsService.getGeojsonValuesdrowPolygon(le.getPkd(),le.getPkf(),le.getRoute_name(),le.getVoie());

    }



    @PostMapping("previewPointResult")
    public List<String> previewPointResult(@RequestBody Ponctuel_Events le){
        System.out.println(le);
        return this.lrsService.getGeojsonValuesdrowPoint(le.getPkEvent(),le.getRoute_name(),le.getVoie());

    }

    @PostMapping("getRouteIdByName")
    public Long getRouteIdByName(@RequestBody String le){
        System.out.println(le);
        return this.lrsService.getRouteIdByName(le);

    }


    @PostMapping("/addNewPointEvent")
    public ResponseEntity<?> addNewPointEventFromMap(@RequestBody MapPoint Le) {
        System.out.println(Le);
        Integer ver = this.lrsService.addNewPointFromMapService(Le);

        if (ver == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("il ya une erreur ");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

        @PostMapping("/addNewLineEventFromMap")
    public ResponseEntity<?> addNewLineEventFromMap(@RequestBody LinePoints Le) {
            System.out.println(Le);
            System.out.println(Le);
        Integer ver = this.lrsService.addNewLineEventFromMap(Le);

        if (ver == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("il ya une erreur ");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getAllEventType")
    public ResponseEntity<?> getAllTypeEvents() {
      List<String> eventsType = this.lrsService.getAllEventType();
        return new ResponseEntity<>(eventsType,HttpStatus.OK);

    }


  @GetMapping("/getreference")
    public ResponseEntity<String> getref() {
      String ref = this.lrsService.getref();
        return new ResponseEntity<String>(ref,HttpStatus.OK);
    }



    @GetMapping("/getProvinces")
    public ResponseEntity<?> getProvinces() {
        List<String> eventsType = this.lrsService.getProvinces();
        return new ResponseEntity<>(eventsType,HttpStatus.OK);

    }


    @PostMapping("/getProvinceByName")
    public ResponseEntity<?> getProvinceByName(@RequestBody String dd) {
        System.out.println(dd);
        Province province = this.lrsService.getProvinceByName(dd);
        return new ResponseEntity<>(province,HttpStatus.OK);

    }



    @PostMapping("/sectionAdd")
    public ResponseEntity<?> Addsection(@RequestBody String Le) {
        Integer ver = this.lrsService.AddsectionService(Le);
        System.out.println("from controller");
        System.out.println(Le);
        if (ver == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("please verify that the lines intersects");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/addSectioncsv")
    public ResponseEntity<?> addSectioncsv(@RequestBody RefModel Le) {
        System.out.println(Le);
        Integer ver = this.lrsService.addSectioncsv(Le);
        System.out.println("from controller");

        if (ver == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("please verify that the lines intersects");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/queryLinearData")
    public ResponseEntity<?> query(@RequestBody String Le) {
        System.out.println(Le);
        List<Linear_Event> ver = this.lrsService.queryLinearData(Le);


            return new ResponseEntity<>(ver ,HttpStatus.OK);



    }


    @PostMapping("/queryLinearData2")
    public ResponseEntity<?> query2(@RequestBody QueryLineData Le) {
        System.out.println(Le);
        List<Map<String,?>> ver = this.lrsService.queryLinearData2(Le);

        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);

    }

    @PostMapping("/getPointJson")
    public ResponseEntity<?> getPointJson(@RequestBody List<Double> Le) {
        System.out.println(Le);
        List<String> ver = this.lrsService.getPointJson2(Le);
        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }



    @PostMapping("/getPointEventJson")
    public ResponseEntity<?> getPointEventJson(@RequestBody String Le) {
        System.out.println(Le);
        List<String> ver = this.lrsService.getPointEventJson(Le);
        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }


@PostMapping("/getLineEventJson")
    public ResponseEntity<?> getLineEventJson(@RequestBody String Le) {
        System.out.println(Le);
        List<String> ver = this.lrsService.getLineEventJson(Le);
        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }

    @PostMapping("/getProvinceJson")
    public ResponseEntity<?> getProvinceJson(@RequestBody String Le) throws SQLException, IOException {
        System.out.println(Le);
            String ver = this.lrsService.getProvinceJson(Le);
        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }

    @PostMapping("/getProvincesJson")
    public ResponseEntity<?> getProvincesJson(@RequestBody List<Province> Le) throws SQLException, IOException {
        System.out.println(Le);
        List<String> ver = this.lrsService.getProvincesJson(Le);
        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }


    @PostMapping("/getLineJson")
    public ResponseEntity<?> getLineJson(@RequestBody List<Double> Le) {
        System.out.println(Le);
        List<String> ver = this.lrsService.getLineJson(Le);

        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }

    @PostMapping("/getBBox")
    public ResponseEntity<?> getBBox(@RequestBody String Le) {
        System.out.println(Le);
        List<Double> ver = this.lrsService.getBBox(Le);

        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }


    @PostMapping("/queryLinearAndPonctual")
    public ResponseEntity<?> queryLinearAndPonctual(@RequestBody QueryLineData Le) {

        List<Map<String,?>> ver = this.lrsService.queryLinearAndPonctual(Le);

        System.out.println(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);

    }


    @PostMapping("/queryPonctuelData")
    public ResponseEntity<?> queryPonctuel(@RequestBody String Le) {
        System.out.println(Le);
        List<Ponctuel_Events> ver = this.lrsService.queryPonctuelData(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }

    @PostMapping("/queryPonctuelDataForMap")
    public ResponseEntity<?> queryPonctuelDataForMap(@RequestBody ponctuelDataForMapReq Le) {


        String regex = ".*[a-zA-Z].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherText = pattern.matcher(Le.getValeur());
        Boolean textMatches = matcherText.matches();
        if(textMatches){
            String request = "select  * from ponctuel_Events where "+ Le.getAttribute().toLowerCase() + " " + Le.getOperateur() + " " + "\'" + Le.getValeur() + "\'" + " and event_type_id =  " +Le.getThematiqueid();
            return new ResponseEntity<>(this.lrsService.queryPonctuelDataForMap(request),HttpStatus.OK);

        }else{
            String request = "select  * from ponctuel_Events where "+ Le.getAttribute().toLowerCase() + " " + Le.getOperateur() + " " + Le.getValeur() + " and event_type_id =  " +Le.getThematiqueid();
            return new ResponseEntity<>(this.lrsService.queryPonctuelDataForMap(request),HttpStatus.OK);

        }



    }

    @PostMapping("/queryLinearDataForMap")
    public ResponseEntity<?> queryLinearDataForMap(@RequestBody linearDataForMapReq Le) {

        String regex = ".*[a-zA-Z].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherText = pattern.matcher(Le.getValeur());
        Boolean textMatches = matcherText.matches();
        if(textMatches){
            String request = "select  * from linear_event where "+ Le.getAttribute().toLowerCase() + " " + Le.getOperateur() + " " + "\'" + Le.getValeur() + "\'" + " and event_type_id =  " +Le.getThematiqueid();
            return new ResponseEntity<>(this.lrsService.queryLinearDataForMap(request),HttpStatus.OK);

        }else{
            String request = "select  * from linear_event where "+ Le.getAttribute().toLowerCase() + " " + Le.getOperateur() + " " + Le.getValeur() + " and event_type_id =  " +Le.getThematiqueid();
            return new ResponseEntity<>(this.lrsService.queryLinearDataForMap(request),HttpStatus.OK);

        }

    }



    @PostMapping("/getDtatapoint")
    public ResponseEntity<?> getDtatapoint(@RequestBody String Le) {
        System.out.println(Le);
        List<Ponctuel_Events> ver = this.lrsService.getDtatapoint(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }
    @PostMapping("/getDtataLine")
    public ResponseEntity<?> getDtataLine(@RequestBody String Le) {
        System.out.println(Le);
        List<Linear_Event> ver = this.lrsService.getDtataLine(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }


    @PostMapping("/queryPonctuelDataS")
    public ResponseEntity<?> queryPonctuelDataS(@RequestBody QueryPonctualData Le) {
        System.out.println(Le);
        List<Map<String,?>>  ver = this.lrsService.queryPonctuelDataS(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }


    @PostMapping("/getRouteNameByRouteId")
    public ResponseEntity<?> getRouteNameByRouteId(@RequestBody Long Le) {
        System.out.println(Le);
        String ver = this.lrsService.getRouteNameByRouteId(Le);
            return new ResponseEntity<>(ver ,HttpStatus.OK);
    }


    @PostMapping("/queryPonctuelDataP")
    public ResponseEntity<?> queryPonctuelDataP(@RequestBody QueryPonctualData Le) {
        System.out.println(Le);
        List<Map<String,?>>  ver = this.lrsService.queryPonctuelDataP(Le);
        return new ResponseEntity<>(ver ,HttpStatus.OK);
    }




    @PostMapping("/addNewSection")
    public ResponseEntity<?> addNewsectionFromMap(@RequestBody NewRouteModel Le) {
        Integer ver = this.lrsService.addNewSectionService(Le);
        System.out.println(Le.getName());
        System.out.println(Le.getRoute());
        if (ver == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("please verify that the lines intersects");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/addRef")
    public ResponseEntity<?> addRef(@RequestBody List<RefModel> Le) {

        Integer verrr = this.lrsService.addNewRefVer(Le);
        if(verrr == 0){
            Integer ver = this.lrsService.addNewRef(Le);
            System.out.println(Le.get(0).getRoute_name());
            System.out.println(Le.get(0).getVoie());
            System.out.println(Le.get(0).getGeometry());
            if (ver == 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("please verify that the lines intersects");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }


        }
        else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("please verify that the lines intersects");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }




    }

    @PostMapping("/createSynoptique")
    public ResponseEntity<?> createSynoptique(@RequestBody Synoptique_param Le) {
        List<DataSynoptique> ver = this.lrsService.createSynoptique(Le);

            return new ResponseEntity<>(ver,HttpStatus.OK);

    }

    @PostMapping("/createAdvancedSynoptique")
    public ResponseEntity<?> createAdvancedSynoptique(@RequestBody Synoptique_param Le) {
        List<DataSynoptique> ver = this.lrsService.createAdvancedSynoptique(Le);

            return new ResponseEntity<>(ver,HttpStatus.OK);

    }

    @PostMapping("/createSynoptiqueLast10")
    public ResponseEntity<?> createSynoptiqueLast10(@RequestBody Synoptique_param Le) {
        List<DataSynoptique> ver = this.lrsService.createSynoptiqueLast10(Le);

            return new ResponseEntity<>(ver,HttpStatus.OK);

    }

    @GetMapping(value = "video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideos(@PathVariable String title, @RequestHeader("Range") String range) {
        System.out.println("range in bytes() : " + range);
        return lrsService.getVideo(title);
    }





   @PostMapping("/changePKByAtt")
   public ResponseEntity<?> changePKByAtt(@RequestBody ClassAttributes Le){
       System.out.println(Le);
       this.lrsService.changePKByAtt(Le);
       return new ResponseEntity<>(HttpStatus.OK);
   }

   @PostMapping("/deleteByAtt")
   public ResponseEntity<?> deleteByAtt(@RequestBody ClassAttributes Le){
       System.out.println(Le);
       this.lrsService.deleteByAtt(Le);
       return new ResponseEntity<>(HttpStatus.OK);
   }


   @PostMapping("/changePKByID")
   public ResponseEntity<?> changePKByID(@RequestBody Lrs_routes Le){
       System.out.println(Le);
       this.lrsService.changePKByID(Le);
       return new ResponseEntity<>(HttpStatus.OK);
   }

   @PostMapping("/deleteByID")
   public ResponseEntity<?> deleteByID(@RequestBody Lrs_routes Le){
       System.out.println(Le);
       this.lrsService.deleteByID(Le);
       return new ResponseEntity<>(HttpStatus.OK);
   }

   @PostMapping("/change_name")
   public ResponseEntity<?> change_name(@RequestBody ChangeName Le){
       System.out.println(Le);
       this.lrsService.changeName(Le);
       return new ResponseEntity<>(HttpStatus.OK);
   }

 @PostMapping("/getVideosList")
   public ResponseEntity<?> getVideosList(@RequestBody Linear_Event Le){
       System.out.println(Le);
        List <Video> videos = this.lrsService.getVideosList(Le);
       return new ResponseEntity<>(videos, HttpStatus.OK);
   }


   @PostMapping("/getPositionfeature")
   public ResponseEntity<?> getPositionfeature(@RequestBody Ponctuel_Events pp){
       System.out.println(pp);
        String json = this.lrsService.getPositionfeature(pp.getPkEvent(),pp.getVoie(),pp.getRoute_name());
       return new ResponseEntity<>(json, HttpStatus.OK);
   }

   @PostMapping("/getdistinctValues")
   public ResponseEntity<?> getdistinctValues(@RequestBody DistinctValue pp){
       System.out.println(pp);
       if(pp.getData().equals("ponctuel")){
           List<String> json = this.lrsService.getdistinctValuesP(pp);
           return new ResponseEntity<>(json,HttpStatus.OK);
       }else if(pp.getData().equals("lineaire")){
           List<String> json = this.lrsService.getdistinctValuesL(pp);
           return new ResponseEntity<>(json,HttpStatus.OK);
       }

       ErrorResponse errorResponse = new ErrorResponse();
       errorResponse.setMessage("il ya une erreur ");
       return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

   }


   @PostMapping("/getdistinctAttributeValD")
   public ResponseEntity<?> getdistinctAttributeValD(@RequestBody Linear_Event pp){
       System.out.println(pp);

           List<String> json = this.lrsService.getdistinctValuesPpchaD(pp);
           return new ResponseEntity<>(json,HttpStatus.OK);
   }
   @PostMapping("/getdistinctAttributeValDV")
   public ResponseEntity<?> getdistinctAttributeValDV(@RequestBody Linear_Event pp){
       System.out.println(pp);

           List<String> json = this.lrsService.getdistinctValuesPpchaDV(pp);
           return new ResponseEntity<>(json,HttpStatus.OK);
   }

   @PostMapping("/getdistinctAttributeValF")
   public ResponseEntity<?> getdistinctAttributeValF(@RequestBody Linear_Event pp){
       System.out.println(pp);

           List<String> json = this.lrsService.getdistinctValuesPpchaF(pp);
       System.out.println(json);
       System.out.println("achraf");
           return new ResponseEntity<>(json,HttpStatus.OK);
   }

   @PostMapping("/getdistinctAttributeValFV")
   public ResponseEntity<?> getdistinctAttributeValFV(@RequestBody Linear_Event pp){
       System.out.println(pp);

           List<String> json = this.lrsService.getdistinctValuesPpchaF(pp);
       System.out.println(json);
       System.out.println("achraf");
           return new ResponseEntity<>(json,HttpStatus.OK);
   }


   @PostMapping("/getCurrentTime")
   public ResponseEntity<?> getCurrentTime(@RequestBody videoScrolling pp){
       System.out.println(pp);
        Long mesure = this.lrsService.getCurrentTime(pp);
       return new ResponseEntity<>(mesure, HttpStatus.OK);
   }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadImageFile(@RequestParam(required = false, name ="file") MultipartFile file,@RequestParam("data") String data,@RequestParam("event_type") String event_type ) throws IOException {

        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());
        System.out.println(file.getContentType());
        System.out.println(file.getSize());
        System.out.println(data);
        Gson g = new Gson();
        String path = new ClassPathResource("static/images/").getFile().getAbsolutePath();
        System.out.println(path);
        if(file != null){
            Files.copy(file.getInputStream(), Paths.get(path + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING );
            System.out.println(event_type);
        }

        if(event_type.equals("ponctuel")) {
            System.out.println("testtest");
            Ponctuel_Events s = g.fromJson(data, Ponctuel_Events.class);
            if (file != null) {
                String image = "images/" + file.getOriginalFilename();
                s.setImage(image);
                System.out.println("hello mother fucker");
                System.out.println(s);
            }

            return new ResponseEntity<>(this.lrsService.savePonctuelEvent(s), HttpStatus.OK);

        }

        if(event_type.equals("linear")) {
            System.out.println("testtest");
            System.out.println(data);
            Linear_Event s = g.fromJson(data, Linear_Event.class);
            System.out.println(s);
            if (file != null) {
                String image = "images/" + file.getOriginalFilename();
                s.setImage(image);
            }

            return new ResponseEntity<>(this.lrsService.saveLinearEvent(s), HttpStatus.OK);

        }


        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("somthing goes wrong");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @PostMapping("/uploadFileNewEvent")
    public ResponseEntity<?> uploadFileNewEvent(@RequestParam(required = false, name ="file") MultipartFile file,@RequestParam("data") String data,@RequestParam("event_type") String event_type ) throws IOException {
        Random rand = new Random(); //instance of random class
        int upperbound = 1000000000;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);


        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());
        System.out.println(file.getContentType());
        System.out.println(file.getSize());
        System.out.println(data);
        Gson g = new Gson();
        String path = new ClassPathResource("static/images/").getFile().getAbsolutePath();
        System.out.println(path);
        if(file != null){
            Files.copy(file.getInputStream(), Paths.get(path + File.separator + file.getOriginalFilename()+"_"+ int_random*100+".jpg"), StandardCopyOption.REPLACE_EXISTING );
            System.out.println(event_type);
        }

        if(event_type.equals("ponctuel")) {
            System.out.println("testtest");
            MapPoint s = g.fromJson(data, MapPoint.class);
            if (file != null) {
                String image = "images/" + file.getOriginalFilename() +"_"+ int_random*100 +  ".jpg";
                s.setImage(image);

                System.out.println(image);
                System.out.println("hello mother fucker");
                System.out.println(s);

                Integer ver = this.lrsService.addNewPointFromMapService2(s);

                if (ver == 0) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.setMessage("il ya une erreur ");
                    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
                }



            }

//            return new ResponseEntity<>(this.lrsService.savePonctuelEvent(s), HttpStatus.OK);


        }

        if(event_type.equals("linear")) {
            System.out.println("testtest");
            System.out.println(data);
            LinePoints s = g.fromJson(data, LinePoints.class);
            System.out.println(s);
            if (file != null) {
                String image = "images/" + file.getOriginalFilename() +"_"+ int_random*100 +  ".jpg";
                s.setImage(image);
                System.out.println("achraf");
                System.out.println(s);
            }

//            return new ResponseEntity<>(this.lrsService.saveLinearEvent(s), HttpStatus.OK);
            Integer ver = this.lrsService.addNewLineEventFromMap2(s);

            if (ver == 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("il ya une erreur ");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        }


        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("somthing goes wrong");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }




    @PostMapping("/saveEventvideo")
    public ResponseEntity<?> saveEventvideo(@RequestParam(required = false, name ="file") MultipartFile file,@RequestParam("pkd") Double pkd,@RequestParam("pkf") Double pkf,@RequestParam("selectedRoute") String selectedRoute,@RequestParam("videoName") String videoName,@RequestParam("selectedVoie") int selectedVoie ) throws IOException {

        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());
        System.out.println(file.getContentType());
        System.out.println(file.getSize());
        System.out.println(selectedVoie);
        Gson g = new Gson();
        String path = new ClassPathResource("videos/").getFile().getAbsolutePath();


//        String absolutePath = context.getRealPath("resources/uploads");
//        String FORMAT="classpath:videos/%s.mp4";


        if(file != null){
            System.out.println("hello hello");
            Long lrsId =  this.lrsService.getRouteId(selectedRoute,pkd,selectedVoie);

            Optional<Lrs_routes> lrs =  this.lrsService.getRouteById(lrsId);
            Files.copy(file.getInputStream(), Paths.get( path + File.separator + videoName+".mp4"), StandardCopyOption.REPLACE_EXISTING );            System.out.println(pkd);

            String image = "video/" + videoName;
            Video vid = Video.builder().pkd(pkd).pkf(pkf).voie(selectedVoie).name(videoName).path(image).lrs_routes(lrs.orElseThrow()).build();
           this.lrsService.saveVideo(vid);

            return new ResponseEntity<>(HttpStatus.OK);

        }
        else{
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("somthing goes wrong");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }


    }







    @GetMapping("/users")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<?> saveUser(@RequestBody MyUser user){
        this.userService.saveUser(user);
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }
    @PostMapping("/role/save")
    public ResponseEntity<?> saveRole(@RequestBody Role role){
        return new ResponseEntity<>(this.userService.saveRole(role), HttpStatus.OK);
    }
    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser roleToUser){
        this.userService.addRoleToUser(roleToUser.getUserName(), roleToUser.getRoleName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getUsers")
    public ResponseEntity<?> getUsers(){
        this.userService.getUsers();
        return new ResponseEntity<>(this.userService.getUsers(),HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody MyUser myUser){
        System.out.println(myUser);
        return new ResponseEntity<>(this.userService.save(myUser),HttpStatus.OK);
    }

    @PostMapping("/getUser")
    public ResponseEntity<?> getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorisqtionHeader = request.getHeader(AUTHORIZATION);
        if(authorisqtionHeader != null && authorisqtionHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorisqtionHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                MyUser user = userService.getUser(username);
                return new ResponseEntity<>(user,HttpStatus.OK);

            }catch (Exception e){

            }
    }else {
        throw new RuntimeException("refresh tocken is missing");
    }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public void  refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorisqtionHeader = request.getHeader(AUTHORIZATION);

        if(authorisqtionHeader != null && authorisqtionHeader.startsWith("Bearer ")){
            try{
                String refreshToken = authorisqtionHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                MyUser user = userService.getUser(username);

                String accesToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> token = new HashMap<>();

                token.put("access_token",accesToken);
                token.put("refresh_token",refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),token);

            }catch(Exception e){

                response.setHeader("err", e.getMessage());
//                   response.sendError(FORBIDDEN.value());


                Map<String,String> err = new HashMap<>();

                err.put("error",e.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),err);

            }



        }else {
            throw new RuntimeException("refresh tocken is missing");
        }

    }








    }
