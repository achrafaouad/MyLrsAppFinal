package com.example.MyLrsAppFinal.service;

import com.example.MyLrsAppFinal.Models.LinearEntityRepo;
import com.example.MyLrsAppFinal.Models.*;
import com.example.MyLrsAppFinal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

@Service
public class LrsService {
    private EventParamsRepository eventParamsRepository;
    private UserRepo userRepo;
    private EventTypeRepository eventTypeRepository;
    private LinearEventReposiory linearEventReposiory;
    private LrsRoutesRepository lrsRoutesRepository;
    private PonctuelEventReposiotory ponctuelEventReposiotory;
    private ProvinceRepository provinceRepository;
    private ReferenceRepository referenceRepository;
    private RegionRepository regionRepository;
    private SymbologyRepo symbologyRepo;
    private LinearEntityRepo linearEntityRepo;
    private PonctuelEntityRepo ponctuelEntityRepo;
    private Integer id;
    private ResourceLoader resourceLoader;
    private VideoRepository videoRepository;
    private ProfilRepository profilRepository;

    private static final String FORMAT="classpath:videos/%s.mp4";
    



    @Autowired
    LrsService(SymbologyRepo symbologyRepo, EventParamsRepository eventParamsRepository, UserRepo userRepo, EventTypeRepository eventTypeRepository, LinearEventReposiory linearEventReposiory, LrsRoutesRepository lrsRoutesRepository, PonctuelEventReposiotory ponctuelEventReposiotory, ProvinceRepository provinceRepository, ReferenceRepository referenceRepository, RegionRepository regionRepository, LinearEntityRepo linearEntityRepo, PonctuelEntityRepo ponctuelEntityRepo, ResourceLoader resourceLoader, VideoRepository videoRepository, ProfilRepository profilRepository) throws IOException {

        this.eventParamsRepository = eventParamsRepository;
        this.userRepo = userRepo;
        this.eventTypeRepository = eventTypeRepository;
        this.linearEventReposiory = linearEventReposiory;
        this.lrsRoutesRepository = lrsRoutesRepository;
        this.ponctuelEventReposiotory = ponctuelEventReposiotory;
        this.provinceRepository = provinceRepository;
        this.referenceRepository = referenceRepository;
        this.symbologyRepo = symbologyRepo;
        this.regionRepository = regionRepository;
        this.linearEntityRepo = linearEntityRepo;
        this.ponctuelEntityRepo = ponctuelEntityRepo;
        this.resourceLoader = resourceLoader;
        this.videoRepository = videoRepository;
        this.profilRepository = profilRepository;
    }










    public int AddsectionService(String le){
        try{
            System.out.println("from service");
            System.out.println(le);

            this.linearEventReposiory.procAddSegmentToRoute(le);
            this.lrsRoutesRepository.indexing();
            return 0;
        }catch(Exception e){
            return 1;
        }

    }


    public List<Event_params> getParams(String eventName) {

        EventType eventType = this.eventTypeRepository.findEventTypeName(eventName);

        return  this.eventParamsRepository.findByEventType(eventType);

    }



       public List<Event_params> getAllParams() {

        return  this.eventParamsRepository.findAll();

    }

 public List<Event_params> getEventParamsById(Long id) {

        Optional<EventType> eventType = this.eventTypeRepository.findById(id);
        System.out.println(eventType);
        return  this.eventParamsRepository.findByEventType(eventType.orElseThrow());

    }

 public List<String> getProvinces() {
        List<Province> eventType = this.provinceRepository.findAll();
        List<String> mesProvinces = new ArrayList();
        for(Province i : eventType){
            mesProvinces.add(i.getName());
        }
        return  mesProvinces;
    }


    public Province getProvinceByName(String name) {
        Province province = this.provinceRepository.findProvinceByName(name);
        System.out.println(name);
        System.out.println(province);
        return  province;

    }


    public Optional<EventType> findEvtpById(Long id){
        Optional<EventType> eventType = this.eventTypeRepository.findById(id);
        return eventType;
    }
    public List<EventType> findEvts(){
        List<EventType> eventType = this.eventTypeRepository.findAll();
        return eventType;
    }

   public void processEventExistLine(List<LineEventFromFront> Le) {

       EventType evtt = this.eventTypeRepository.findEventTypeName(Le.get(0).getEventType());
       System.out.println(evtt);
       for(LineEventFromFront L :Le){
           this.linearEventReposiory.procInsertLineEvent(L.getPkd(),L.getPkf(),L.getRoute_name(),L.getVoie(),L.getC1(),L.getC2(),L.getC3(),L.getD1(),L.getD2(),L.getD3(),L.getT1(),L.getT2(),L.getT3(),evtt.getId());

       }
       this.lrsRoutesRepository.indexingLineEvent();
   }



    public void addPointEvent(List<PointEventFromFront> Le){
        EventType evtt = this.eventTypeRepository.findEventTypeName(Le.get(0).getEventType());

        for(PointEventFromFront L : Le){
            System.out.println(evtt);
            System.out.println("ffffffffff");
            System.out.println(evtt.getId());
            this.ponctuelEventReposiotory.procInsertEvent(L.getPkEvent(), L.getRoute_name(),L.getVoie(),L.getC1(),L.getC2(),L.getC3(),L.getD1(),L.getD2(),L.getD3(),L.getT1(),L.getT2(),L.getT3(),evtt.getId());

        }
        this.lrsRoutesRepository.indexingPointEvent();
    }



    public Symbology updateSymbology(Symbology le) {
        return this.symbologyRepo.save(le);
    }

    public void updateColors(LineColorFomFront le) {
        EventType eventType = this.eventTypeRepository.findEventTypeName(le.getEventType());
        eventType.setStyle(le.getColors());
        this.eventTypeRepository.save(eventType);

    }


    public void updatePointColors(LineColorFomFront le) {
        System.out.println("ana f les couleurs de point" );
        System.out.println(le);
        EventType eventType = this.eventTypeRepository.findEventTypeName(le.getEventType());

        eventType.setPointStyle(le.getColors());
        this.eventTypeRepository.save(eventType);



    }

    public Long addEventTypeService(String le) {
        this.eventTypeRepository.save(EventType.builder().name(le).build());

        return this.eventTypeRepository.findEventTypeName(le).getId();
    }
    public Long addEventTypeService2(String le) {
        this.eventTypeRepository.save(EventType.builder().name(le).style("{\"remplissageC\":\"#ffffff\",\"colorBor\":\"#ff0000\",\"strock\":3}").pointStyle("{\"radius\":10,\"color\":\"#f30707\",\"Ocolor\":\"#d9d9d9\",\"strokWidht\":5}").build());

        return this.eventTypeRepository.findEventTypeName(le).getId();
    }

    public Long deletethAtAll(Long le) {
        this.eventParamsRepository.deleteByMyid(le);
        this.eventTypeRepository.deleteByMyid(le);

       return 1L;

    }

    public void addEventParams(SaveParams le) {
        Optional<EventType> eventType = this.eventTypeRepository.findById(le.getId_event());
        this.eventParamsRepository.save(Event_params.builder().champs_event(le.getChamps_event()).champ_db_stock(le.getChamp_db_stock()).eventType(eventType.orElseThrow()).build());
    }

    public Optional<Symbology> getStyleByid(Long id){
        return this.symbologyRepo.findById(id);
    }



    public int verifyEventLine( List<LineEventFromFront> Le){
        try{
            for(int i=0;i< Le.size();i++){
                this.id = i+1;
                System.out.println(i);
                this.linearEventReposiory.verifySegment(Le.get(i).getPkd(),Le.get(i).getPkf(), Le.get(i).getRoute_name(), Le.get(i).getVoie());

            }
            return 0;
        }catch(Exception e){
            System.out.println("catch");
            return this.id;
        }
    }

//todo

    public int verifyEventPoint(List<PointEventFromFront> le){
        try{
            for(int i=0;i< le.size();i++){
                this.id = i+1;
                System.out.println(i);
                System.out.println(le.get(i).getPkEvent());
                this.ponctuelEventReposiotory.verifyPoint(le.get(i).getPkEvent(), le.get(i).getRoute_name(), le.get(i).getVoie());

            }
            return 0;
        }catch(Exception e){
            return this.id;
        }
    }



    public List<String> getGeojson(){
        return this.linearEventReposiory.getGeojsonValues();
    }



public List<String> getGeojsonValuesdrowPolygon( Double pkDebut ,Double pkFin , String route_name1 , int voie1){
        return this.linearEventReposiory.getGeojsonValuesdrowPolygon(pkDebut,pkFin , route_name1,voie1);
    }
public List<String> getGeojsonValuesdrowPoint( Double event_position , String route_name1 , int voie1){
        return this.ponctuelEventReposiotory.getGeojsonValuesdrowPoint(event_position , route_name1,voie1);
    }

    public List<String> getRouteNames(){
        return this.lrsRoutesRepository.getAllROuteNames();
    }

    public Long getRouteIdByName(String le){
        Lrs_routes rr =  this.lrsRoutesRepository.findByName(le);
        return rr.getRoute_id();
    }




    public Double getByCoordEvent(MapPoint p){
        return this.ponctuelEventReposiotory.getByCoordEvent(p.getX(),p.getY());

    }

    public int addNewPointFromMapService(MapPoint p){
        try{
            System.out.println("from service");

            Long id  = this.eventTypeRepository.findEventTypeName(p.getThematique()).getId();
            System.out.println(id);
            this.ponctuelEventReposiotory.procNewPointSep(p.getX(),p.getY(),p.getC1(),p.getC2(),p.getC3(),p.getD1(),p.getD2(),p.getD3(),p.getT1(),p.getT2(),p.getT3(),id);
            this.lrsRoutesRepository.indexingPointEvent();
            return 0;
        }catch(Exception e){
            return 1;
        }

    }


    public int addNewPointFromMapService2(MapPoint p){
        try{
            System.out.println("from service");

            Long id  = this.eventTypeRepository.findEventTypeName(p.getThematique()).getId();
            System.out.println(id);
            this.ponctuelEventReposiotory.procNewPointSep2(p.getX(),p.getY(),p.getC1(),p.getC2(),p.getC3(),p.getD1(),p.getD2(),p.getD3(),p.getT1(),p.getT2(),p.getT3(),p.getImage(),id);
            this.lrsRoutesRepository.indexingPointEvent();
            return 0;
        }catch(Exception e){
            return 1;
        }

    }



    public int addNewLineEventFromMap(LinePoints p){
        try{
            System.out.println("from service");
            Long id  = this.eventTypeRepository.findEventTypeName(p.getThematique()).getId();
            this.linearEventReposiory.procNewlinesSep(p.getX1(),p.getY1(),p.getX2(),p.getY2(),p.getC1(),p.getC2(),p.getC3(),p.getD1(),p.getD2(),p.getD3(),p.getT1(),p.getT2(),p.getT3(),id);
            this.lrsRoutesRepository.indexingLineEvent();
            return 0;
        }catch(Exception e){
            return 1;
        }

    }
    public int addNewLineEventFromMap2(LinePoints p){
        try{
            System.out.println("from service");
            Long id  = this.eventTypeRepository.findEventTypeName(p.getThematique()).getId();
            this.linearEventReposiory.procNewlinesSep2(p.getX1(),p.getY1(),p.getX2(),p.getY2(),p.getC1(),p.getC2(),p.getC3(),p.getD1(),p.getD2(),p.getD3(),p.getT1(),p.getT2(),p.getT3(),p.getImage(),id);
            this.lrsRoutesRepository.indexingLineEvent();
            return 0;
        }catch(Exception e){
            return 1;
        }

    }



    public List<String> getAllEventType() {
        return this.eventTypeRepository.indEventsTypeName();
    }

    public String getref() {
        int i = 0;
        int j = 0;

       for(Lrs_routes route : this.lrsRoutesRepository.findAll()){
            if(route.getReference().equals(1)){
                i = i+1;
            }if(route.getReference().equals(2)){
               j = j+1;
            }

       }
        if(j>0 && i >0){
            return "true";
        }
        return "false";

    }

    public List<Linear_Event> queryLinearData(String le) {

        List<Linear_Event> resultList = this.linearEntityRepo.QueryData(le);

        return resultList;
    }

    public  List<Map<String,String>> queryLinearData2(QueryLineData le) throws SQLException, IOException {
        List<Map<String,String>> resultList2 = new ArrayList<>();
        List<Map<String,?>> resultList = this.linearEventReposiory.QueryData(le.getThematique1(),le.getThematique2());
        int regionIndex = 1;

        for(Map<String, ?> intersection : resultList) {

            System.out.println("\nIndia Region - " + regionIndex);
            System.out.println("============================"
                    + "======================");

            // get entrySet() into Set
            Set<String> setOfIndianStates = intersection.keySet();

            // Collection Iterator
            Iterator<String> iterator =
                    setOfIndianStates.iterator();

            // iterate using while-loop after getting Iterator
            Map<String,String> map = new HashMap<String, String>();
            while(iterator.hasNext()) {

                String key = iterator.next();
                if(key.equals("ROUTE_GEOMETRY") || key.equals("ROUTE_GEOMETRY_1")){
                    Clob varr = (Clob) intersection.get(key);
                    Reader r = varr.getCharacterStream();
                    int j = 0;
                    StringBuffer buffer = new StringBuffer();
                    int ch;
                    while ((ch = r.read())!=-1) {
                        buffer.append(""+(char)ch);
                    }
                    String data = buffer.toString();
                    j++;
                    map.put(key,data);


                }else{
                    System.out.println("State : " + key
                            + "\tCapital : " + intersection.get(key));
                    map.put(key,"" + intersection.get(key));

                }

            }

            // increment region index by 1
            resultList2.add(map);
            regionIndex++;
        }
        return resultList2;
    }

//labalena

    public  List<Map<String,String>> queryLinearAndPonctual(QueryLineData le) throws SQLException, IOException {
        System.out.println(le);
        List<Map<String,String>> resultList2 = new ArrayList<>();
        List<Map<String,?>> resultList = this.linearEventReposiory.QueryDataLP(le.getThematique1(),le.getThematique2());
//        for(Map<String,?> de: resultList){
//            Clob data = (Clob) de.get("route_geometry");
//            System.out.println(data.getSubString(0, (int) data.length()));
//        }

//        achraf aouad

        int regionIndex = 1;
        for(Map<String, ?> intersection : resultList) {

            System.out.println("\nIndia Region - " + regionIndex);
            System.out.println("============================"
                    + "======================");

            // get entrySet() into Set
            Set<String> setOfIndianStates = intersection.keySet();

            // Collection Iterator
            Iterator<String> iterator =
                    setOfIndianStates.iterator();

            // iterate using while-loop after getting Iterator
            Map<String,String> map = new HashMap<String, String>();
            while(iterator.hasNext()) {

                String key = iterator.next();
                if(key.equals("ROUTE_GEOMETRY") || key.equals("ROUTE_GEOMETRY_1")){
                    Clob varr = (Clob) intersection.get(key);
                    Reader r = varr.getCharacterStream();
                    int j = 0;
                    StringBuffer buffer = new StringBuffer();
                    int ch;
                    while ((ch = r.read())!=-1) {
                        buffer.append(""+(char)ch);
                    }
                    String data = buffer.toString();
                    j++;
                    map.put(key,data);


                }else{
                    System.out.println("State : " + key
                            + "\tCapital : " + intersection.get(key));
                    map.put(key,"" + intersection.get(key));

                }

            }

            // increment region index by 1
            resultList2.add(map);
            regionIndex++;
        }


        return resultList2;
    }


 public  List<String> getLineJson(List<Double> le) throws SQLException, IOException {

     List<String> resultList = new ArrayList<>();

        for(Double data :le){
            String data1;
            Clob varr = this.linearEventReposiory.getLineJson(data);
            Reader r = varr.getCharacterStream();
            int j = 0;
            StringBuffer buffer = new StringBuffer();
            int ch;
            while ((ch = r.read())!=-1) {
                buffer.append(""+(char)ch);
            }
            System.out.println(buffer.toString());
            data1= buffer.toString();
            j++;
            resultList.add(data1);
        }


        return resultList;
    }


    public  List<Double> getBBox(String le) {




            List<Double> varr = this.lrsRoutesRepository.getBBox(le);



        return varr;
    }

    public Optional<Ponctuel_Events> savePonctuelEvent(Ponctuel_Events p){
        Optional<Ponctuel_Events> pp = this.ponctuelEventReposiotory.findById(p.getId());
        pp.orElseThrow().setImage(p.getImage());
        pp.orElseThrow().setC1(p.getC1());
        pp.orElseThrow().setC2(p.getC2());
        pp.orElseThrow().setC3(p.getC3());
        pp.orElseThrow().setD1(p.getD1());
        pp.orElseThrow().setD2(p.getD2());
        pp.orElseThrow().setD3(p.getD3());
        pp.orElseThrow().setT1(p.getT1());
        pp.orElseThrow().setT2(p.getT2());
        pp.orElseThrow().setT3(p.getT3());
        this.ponctuelEventReposiotory.save(pp.orElseThrow());
        return this.ponctuelEventReposiotory.findById(p.getId());
    }

       public Optional<Linear_Event> saveLinearEvent(Linear_Event p){
        Optional<Linear_Event> pp = this.linearEventReposiory.findById(p.getId());
        pp.orElseThrow().setImage(p.getImage());
        pp.orElseThrow().setC1(p.getC1());
        pp.orElseThrow().setC2(p.getC2());
        pp.orElseThrow().setC3(p.getC3());
        pp.orElseThrow().setD1(p.getD1());
        pp.orElseThrow().setD2(p.getD2());
        pp.orElseThrow().setD3(p.getD3());
        pp.orElseThrow().setT1(p.getT1());
        pp.orElseThrow().setT2(p.getT2());
        pp.orElseThrow().setT3(p.getT3());
        this.linearEventReposiory.save(pp.orElseThrow());
        return this.linearEventReposiory.findById(p.getId());
    }

    public  List<String> getPointJson2 (List<Double> le) {

     List<String> resultList = new ArrayList<>();

        for(Double data :le){
            String varr = this.ponctuelEventReposiotory.getPointJson(data);
            resultList.add(varr);
        }


        return resultList;
    }

    public  List<String> getPointEventJson (String le) {
        EventType eventType = this.eventTypeRepository.findEventTypeName(le);

        List<String> varr = this.ponctuelEventReposiotory.getPointEventJson(eventType.getId());
        varr.forEach(System.out::println);
        return varr;

    }
    public  List<String> getLineEventJson (String le) {
        EventType eventType = this.eventTypeRepository.findEventTypeName(le);

        List<String> varr = this.linearEventReposiory.getLineEventJson(eventType.getId());
        return varr;

    }
 //todo province
    public  String getProvinceJson(String le) throws SQLException, IOException {
            String data;
            Clob varr = this.provinceRepository.getJson(le);
            Reader r = varr.getCharacterStream();
            int j = 0;
            StringBuffer buffer = new StringBuffer();
            int ch;
            while ((ch = r.read())!=-1) {
                buffer.append(""+(char)ch);
            }
            System.out.println(buffer.toString());
            data= buffer.toString();
            j++;
        return data;
    }

    public  List<String> getProvincesJson(List<Province> le) throws SQLException, IOException {
        List<String> dataJson = new ArrayList<>();

        for(Province he:le){
            dataJson.add(this.getProvinceJson(he.getName()));
        }
        return dataJson;

    }

    public List<Map<String,?>> queryPonctuelDataS(QueryPonctualData le) {

        List<Map<String,?>> resultList = this.ponctuelEventReposiotory.QueryDataS(le.getThematique1(),le.getThematique2());

        return resultList;
    }

    public String getRouteNameByRouteId(Long le) {

        Optional<Lrs_routes> resultList = this.lrsRoutesRepository.findById(le);
        return resultList.orElseThrow().getRoute_name();
    }





    public List<Map<String,?>> queryPonctuelDataP(QueryPonctualData le) {

        List<Map<String,?>> resultList = this.ponctuelEventReposiotory.QueryDataP(le.getThematique1(),le.getThematique2(),le.getPkEvent());

        return resultList;
    }


    public List<Ponctuel_Events> queryPonctuelData(String le) {

        List<Ponctuel_Events> resultList = this.ponctuelEntityRepo.QueryData(le);

        return resultList;
    }

    public List<ponctuelEventWithGeometry> queryPonctuelDataForMap(String request) {
        List<ponctuelEventWithGeometry> newestGeometry = new ArrayList<>();

        for( Ponctuel_Events ver : this.queryPonctuelData(request)){
            ponctuelEventWithGeometry dd = ponctuelEventWithGeometry.builder().id(ver.getId()).route_name(ver.getRoute_name()).pkEvent(ver.getPkEvent()).voie(ver.getVoie()).event_name(ver.getEvent_type().getName()).route_id(ver.getRoute().getRoute_id()).build();
            String varr = this.ponctuelEventReposiotory.getPointJson(dd.getId());
            dd.setJsond(varr);
            newestGeometry.add(dd);
        }

        return newestGeometry;
    }

    public List<LinearEventWithGeometry> queryLinearDataForMap(String request) throws SQLException, IOException {
        List<LinearEventWithGeometry> newestGeometry = new ArrayList<>();
        String data;
        for( Linear_Event ver : this.queryLinearData(request)){
            LinearEventWithGeometry dd = LinearEventWithGeometry.builder().id(ver.getId()).route_name(ver.getRoute_name()).pkd(ver.getPkd()).pkf(ver.getPkf()).voie(ver.getVoie()).event_name(ver.getEvent_type().getName()).route_id(ver.getLrs_routes().getRoute_id()).build();

            Clob varr = this.linearEventReposiory.getLineJson(dd.getId());
            Reader r = varr.getCharacterStream();
            int j = 0;
            StringBuffer buffer = new StringBuffer();
            int ch;
            while ((ch = r.read())!=-1) {
                buffer.append(""+(char)ch);
            }
            System.out.println(buffer.toString());
            data= buffer.toString();
            j++;

            dd.setJsond(data);
            newestGeometry.add(dd);
        }

        return newestGeometry;
    }


    public List<Ponctuel_Events> getDtatapoint(String le) {
        EventType eventType = this.eventTypeRepository.findEventTypeName(le);
        List<Ponctuel_Events> resultList = this.ponctuelEventReposiotory.QueryData(eventType.getId());
        return resultList;
    }

    public List<Linear_Event> getDtataLine(String le) {
        EventType eventType = this.eventTypeRepository.findEventTypeName(le);
        System.out.println(eventType);
        System.out.println("achraf");
        System.out.println(eventType.getId());
        List<Linear_Event> resultList = this.linearEventReposiory.QueryData(eventType.getId());
        return resultList;
    }



    public int addNewSectionService(NewRouteModel le){
        try{
            System.out.println("from service");
            System.out.println(le);

            this.lrsRoutesRepository.procNewSegmentSep(le.getRoute(),le.getName(),le.getVoie(),le.getReference());
            this.lrsRoutesRepository.indexing();
            return 0;
        }catch(Exception e){
            return 1;
        }

    }

    public int addNewRef(List<RefModel> le){

        for(RefModel me : le){
            try{
                System.out.println("from service");
                System.out.println(le);
                String geom = me.getGeometry().replace("type:LineString,coordinates:","'type':'LineString','coordinates':");
                me.setGeometry(geom);
                System.out.println(geom);
                this.lrsRoutesRepository.procNewaddNewRef(me.getRoute_name(),me.getPkd(),me.getPkf(),me.getVoie(),me.getGeometry(),me.getReference());
                this.lrsRoutesRepository.indexing();

            }catch(Exception e){
                return 1;
            }
        }
        return 0;
    }

    public int addSectioncsv(RefModel le){

            try{
                System.out.println("from service");
                System.out.println(le);
                String geom = le.getGeometry().replace("type:LineString,coordinates:","'type':'LineString','coordinates':");
                le.setGeometry(geom);
                System.out.println(geom);
                this.lrsRoutesRepository.procaddSectioncsv(le.getGeometry(),le.getReference(),le.getVoie(),le.getRoute_name());
                this.lrsRoutesRepository.indexing();

            }catch(Exception e){
                return 1;
            }

        return 0;

    }


    public int addNewRefVer(List<RefModel> le){

        for(RefModel me : le){
            try{
                System.out.println("from service");
                System.out.println(le);
                String geom = me.getGeometry().replace("type:LineString,coordinates:","'type':'LineString','coordinates':");
                me.setGeometry(geom);
                System.out.println(geom);
                this.lrsRoutesRepository.procNewaddNewRefVerify(me.getRoute_name(),me.getPkd(),me.getPkf(),me.getVoie(),me.getGeometry(),me.getProvince(),me.getReference());


            }catch(Exception e){
                return 1;
            }
        }
        return 0;

    }

    public void changePKByAtt(ClassAttributes le){

            System.out.println("from service");
            System.out.println(le);

            this.lrsRoutesRepository.changePKByAttproc(le.getPkd1(),le.getPkf1(),le.getRoute_name(),le.getPkd2(),le.getPkf2(),le.getVoie());
            this.lrsRoutesRepository.indexing();


    }


    public void deleteByAtt(ClassAttributes le){

            System.out.println("from service");
            System.out.println(le);

            this.lrsRoutesRepository.deleteByAttproc(le.getPkd1(),le.getPkf1(),le.getRoute_name(),le.getVoie());
            this.lrsRoutesRepository.indexing();


    }
    public void changePKByID(Lrs_routes le){

            System.out.println("from service");
            System.out.println(le);

            this.lrsRoutesRepository.changePKByIDproc(le.getRoute_id(),le.getPkd(),le.getPkf());
            this.lrsRoutesRepository.indexing();


    }
    public void deleteByID(Lrs_routes le){

            System.out.println("from service");
            System.out.println(le);

            this.lrsRoutesRepository.deleteByIDproc(le.getRoute_id());
            this.lrsRoutesRepository.indexing();


    }

    public void addProfil(Profil le){

            System.out.println("from service");
            System.out.println(le);
            le.setDateAjout( new Date());

            this.profilRepository.save(le);

    }
 public List<Profil> getprofiles(){



           return this.profilRepository.findAll();

    }

    public Optional<Profil> profileByid(Long id){

           return this.profilRepository.findById(id);

    }


    public void changeName(ChangeName le){



            this.lrsRoutesRepository.changeName(le.getName(),le.getName1());
            this.lrsRoutesRepository.indexing();


    }

   //brahim irhamni


    public Info getinfo() {
        Double somme = 0D;
        Integer somme2 = 0;
        Double somme3 = 0D;
        for (Linear_Event linear_event : this.linearEventReposiory.findAll()) {
            somme = somme +  (linear_event.getPkf()-linear_event.getPkd())/10000;
        }

        somme2 =this.ponctuelEventReposiotory.findAll().size();
        for (Lrs_routes lrs_routes : this.lrsRoutesRepository.findAll()) {
            somme3 = somme3 +  (lrs_routes.getPkf()-lrs_routes.getPkd())/10000;
        }

        return  Info.builder().kiloLinear(somme).kiloRoute(somme3).nbPoint(somme2).build();
    }

    public List<Datagraph> createGraph() {
        List <EventType> evt = this.eventTypeRepository.findAll();
        List<String> eventsName = new ArrayList<>();
        for(EventType tt: evt){
            eventsName.add(tt.getName());
        }

        Date dt=new Date();
        Integer year=dt.getYear();
        Double somme = 0D;
        List<Datagraph> dss = new ArrayList<>();
        for(String t : eventsName){
            Datagraph ds = new Datagraph();
            ds.setName(t);
        for(var i = 1;i<=12;i++){
            for (Linear_Event linear_event : this.linearEventReposiory.findAll()) {
                if(linear_event.getDate_ajoute() != null){
                if((linear_event.getDate_ajoute().getMonth()) == i && linear_event.getDate_ajoute().getYear() == year){
                    if(linear_event.getEvent_type().getName().equals(t)){
                        somme = somme +  (linear_event.getPkf()-linear_event.getPkd())/10000;
                    }
                }
            }
            }
            if(somme == 0 ){
                somme =null;
            }
            ds.getData().add(somme);
            somme = 0D;
        }

        dss.add(ds);
        }

        return dss;

    }

    public List<Datagraph> getgraphponctuel() {
        List <EventType> evt = this.eventTypeRepository.findAll();
        List<String> eventsName = new ArrayList<>();
        for(EventType tt: evt){
            eventsName.add(tt.getName());
        }

        Date dt=new Date();
        Integer year=dt.getYear();
        Double somme = 0D;
        List<Datagraph> dss = new ArrayList<>();
        for(String t : eventsName){
            Datagraph ds = new Datagraph();
            ds.setName(t);
            for(var i = 1;i<=12;i++){
                for (Ponctuel_Events ponctuel_events : this.ponctuelEventReposiotory.findAll()) {
                    if(ponctuel_events.getDate_ajoute() != null){
                    if((ponctuel_events.getDate_ajoute().getMonth()) == i && ponctuel_events.getDate_ajoute().getYear() == year){
                        if(ponctuel_events.getEvent_type().getName().equals(t)){
                            somme = somme +  1;
                        }
                    }
                }
                }
                if(somme == 0 ){
                    somme =null;
                }
                ds.getData().add(somme);
                somme = 0D;
            }

            dss.add(ds);
        }

        return dss;

    }
    public List<DataSynoptique> createSynoptique2Analyse(Synoptique_param2 le) {


        Date dt=new Date();
        Integer year=dt.getYear() + 1900;
        List<Integer> years = new ArrayList<>();
        years.add(year);
        for(int i =1;i<10;i++){
            years.add(year-i);
        }

        String rtName =  le.getRouteName();
        int voie = le.getVoie();



        List<DataSynoptique> dss = new ArrayList<>();

        System.out.println("hmed hmed");
        List<EventAttribut> eventsAtt = new ArrayList<>();

        eventsAtt.add(EventAttribut.builder().name(le.getEvent1()).attrribute(le.getAttrribute1()).build());

        for(Integer t : years){
            for(EventAttribut eventAtrribute : eventsAtt){

                System.out.println(eventAtrribute);
                for (Linear_Event linear_event : this.linearEventReposiory.findAll()) {
                    if(le.getPkd() != null && le.getPkf() != null){
                        if(linear_event.getPkd() > le.getPkd() && linear_event.getPkf() < le.getPkf()){
                            if(linear_event.getDate_ajoute() != null){
                            if((linear_event.getDate_ajoute().getYear()+1900) == t ){
                            if(linear_event.getVoie() == voie){
                                if(linear_event.getRoute_name().equals(le.getRouteName())){
                                    if(linear_event.getEvent_type().getName().equals(eventAtrribute.getName())){
                                        System.out.println("achraf dj");
                                        if(eventAtrribute.getAttrribute().equals("c1")){
                                            DataSynoptique ds = new DataSynoptique();
                                            ds.setName(linear_event.getC1());
                                            CoordinateSynoptique crrd = new CoordinateSynoptique();
                                            crrd.setX(Integer.toString(t));
                                            crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                            ds.getData().add(crrd);
                                            dss.add(ds);

                                        }
                                        if(eventAtrribute.getAttrribute().equals("c2")){
                                            DataSynoptique ds = new DataSynoptique();
                                            ds.setName(linear_event.getC2());
                                            CoordinateSynoptique crrd = new CoordinateSynoptique();
                                            crrd.setX(Integer.toString(t));
                                            crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                            ds.getData().add(crrd);
                                            dss.add(ds);
                                        }
                                        if(eventAtrribute.getAttrribute().equals("c3")){
                                            DataSynoptique ds = new DataSynoptique();
                                            ds.setName(linear_event.getC3());
                                            CoordinateSynoptique crrd = new CoordinateSynoptique();
                                            crrd.setX(Integer.toString(t));
                                            crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                            ds.getData().add(crrd);
                                            dss.add(ds);
                                        }

                                    }
                                }

                            }
                        }
                        }
                        }
                    }
                }

            }

            for(EventAttribut eventAtrribute : eventsAtt){

                System.out.println(eventAtrribute);
                for (Ponctuel_Events ponctuel_events : this.ponctuelEventReposiotory.findAll()) {
                    if(le.getPkd() != null && le.getPkf() != null){
                        if(ponctuel_events.getPkEvent() > le.getPkd() && ponctuel_events.getPkEvent() < le.getPkf()){
                            if(ponctuel_events.getDate_ajoute() != null){
                            if((ponctuel_events.getDate_ajoute().getYear()+1900) == t ){
                            if(ponctuel_events.getVoie() == voie){
                                if(ponctuel_events.getRoute_name().equals(le.getRouteName())){
                                    if(ponctuel_events.getEvent_type().getName().equals(eventAtrribute.getName())){
                                        System.out.println("achraf dj");
                                        if(eventAtrribute.getAttrribute().equals("c1")){
                                            DataSynoptique ds = new DataSynoptique();
                                            ds.setName(ponctuel_events.getC1());
                                            CoordinateSynoptique crrd = new CoordinateSynoptique();
                                            crrd.setX(Integer.toString(t));
                                            crrd.setY(List.of(ponctuel_events.getPkEvent(),ponctuel_events.getPkEvent() + 10));
                                            ds.getData().add(crrd);
                                            dss.add(ds);

                                        }
                                        if(eventAtrribute.getAttrribute().equals("c2")){
                                            DataSynoptique ds = new DataSynoptique();
                                            ds.setName(ponctuel_events.getC2());
                                            CoordinateSynoptique crrd = new CoordinateSynoptique();
                                            crrd.setX(Integer.toString(t));
                                            crrd.setY(List.of(ponctuel_events.getPkEvent(),ponctuel_events.getPkEvent()));
                                            ds.getData().add(crrd);
                                            dss.add(ds);
                                        }
                                        if(eventAtrribute.getAttrribute().equals("c3")){
                                            DataSynoptique ds = new DataSynoptique();
                                            ds.setName(ponctuel_events.getC3());
                                            CoordinateSynoptique crrd = new CoordinateSynoptique();
                                            crrd.setX(Integer.toString(t));
                                            crrd.setY(List.of(ponctuel_events.getPkEvent(),ponctuel_events.getPkEvent()));
                                            ds.getData().add(crrd);
                                            dss.add(ds);
                                        }

                                    }
                                }

                            }
                            }
                        }
                        }
                    }
                }

            }
        }





        System.out.println("brahim irhamni");
        System.out.println(dss);

        return dss;

    }
    public List<DataSynoptique> createSynoptique2(Synoptique_param2 le) {

        System.out.println("hmed hmed");
        List<DataSynoptique> dss = new ArrayList<>();
        String rtName =  le.getRouteName();
        int voie = le.getVoie();
        List <EventType> evt = this.eventTypeRepository.findAll();
        List<EventAttribut> eventsAtt = new ArrayList<>();


        eventsAtt.add(EventAttribut.builder().name(le.getEvent1()).attrribute(le.getAttrribute1()).build());
        eventsAtt.add(EventAttribut.builder().name(le.getEvent2()).attrribute(le.getAttrribute2()).build());
        eventsAtt.add(EventAttribut.builder().name(le.getEvent3()).attrribute(le.getAttrribute3()).build());


        for(EventAttribut eventAtrribute : eventsAtt){

            System.out.println(eventAtrribute);
            for (Linear_Event linear_event : this.linearEventReposiory.findAll()) {
                if(le.getPkd() != null && le.getPkf() != null){
                    if(linear_event.getPkd() > le.getPkd() && linear_event.getPkf() < le.getPkf()){
                        if(linear_event.getVoie() == voie){
                            if(linear_event.getRoute_name().equals(le.getRouteName())){
                                if(linear_event.getEvent_type().getName().equals(eventAtrribute.getName())){
                                    System.out.println("achraf dj");
                                    if(eventAtrribute.getAttrribute().equals("c1")){
                                        DataSynoptique ds = new DataSynoptique();
                                        ds.setName(linear_event.getC1());
                                        CoordinateSynoptique crrd = new CoordinateSynoptique();
                                        crrd.setX(eventAtrribute.getName());
                                        crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                        ds.getData().add(crrd);
                                        dss.add(ds);

                                    }
                                    if(eventAtrribute.getAttrribute().equals("c2")){
                                        DataSynoptique ds = new DataSynoptique();
                                        ds.setName(linear_event.getC2());
                                        CoordinateSynoptique crrd = new CoordinateSynoptique();
                                        crrd.setX(eventAtrribute.getName());
                                        crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                        ds.getData().add(crrd);
                                        dss.add(ds);
                                    }
                                    if(eventAtrribute.getAttrribute().equals("c3")){
                                        DataSynoptique ds = new DataSynoptique();
                                        ds.setName(linear_event.getC3());
                                        CoordinateSynoptique crrd = new CoordinateSynoptique();
                                        crrd.setX(eventAtrribute.getName());
                                        crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                        ds.getData().add(crrd);
                                        dss.add(ds);
                                    }

                                }
                            }

                        }
                    }
                }
            }

        }

        for(EventAttribut eventAtrribute : eventsAtt){

            System.out.println(eventAtrribute);
            for (Ponctuel_Events ponctuel_events : this.ponctuelEventReposiotory.findAll()) {
                if(le.getPkd() != null && le.getPkf() != null){
                    if(ponctuel_events.getPkEvent() > le.getPkd() && ponctuel_events.getPkEvent() < le.getPkf()){
                        if(ponctuel_events.getVoie() == voie){
                            if(ponctuel_events.getRoute_name().equals(le.getRouteName())){
                                if(ponctuel_events.getEvent_type().getName().equals(eventAtrribute.getName())){
                                    System.out.println("achraf dj");
                                    if(eventAtrribute.getAttrribute().equals("c1")){
                                        DataSynoptique ds = new DataSynoptique();
                                        ds.setName(ponctuel_events.getC1());
                                        CoordinateSynoptique crrd = new CoordinateSynoptique();
                                        crrd.setX(eventAtrribute.getName());
                                        crrd.setY(List.of(ponctuel_events.getPkEvent(),ponctuel_events.getPkEvent() + 10));
                                        ds.getData().add(crrd);
                                        dss.add(ds);

                                    }
                                    if(eventAtrribute.getAttrribute().equals("c2")){
                                        DataSynoptique ds = new DataSynoptique();
                                        ds.setName(ponctuel_events.getC2());
                                        CoordinateSynoptique crrd = new CoordinateSynoptique();
                                        crrd.setX(eventAtrribute.getName());
                                        crrd.setY(List.of(ponctuel_events.getPkEvent(),ponctuel_events.getPkEvent()));
                                        ds.getData().add(crrd);
                                        dss.add(ds);
                                    }
                                    if(eventAtrribute.getAttrribute().equals("c3")){
                                        DataSynoptique ds = new DataSynoptique();
                                        ds.setName(ponctuel_events.getC3());
                                        CoordinateSynoptique crrd = new CoordinateSynoptique();
                                        crrd.setX(eventAtrribute.getName());
                                        crrd.setY(List.of(ponctuel_events.getPkEvent(),ponctuel_events.getPkEvent()));
                                        ds.getData().add(crrd);
                                        dss.add(ds);
                                    }

                                }
                            }

                        }
                    }
                }
            }

        }

        System.out.println("brahim irhamni");
        System.out.println(dss);

        return dss;

    }


    public List<DataSynoptique> createSynoptique(Synoptique_param le) {


        String rtName =  le.getRouteName();
        int voie = le.getVoie();
        List <EventType> evt = this.eventTypeRepository.findAll();
        List<String> eventsName = new ArrayList<>();
        for(EventType tt: evt){
            eventsName.add(tt.getName());
        }


        List<DataSynoptique> dss = new ArrayList<>();

        for(String t : eventsName){
            DataSynoptique ds = new DataSynoptique();
            ds.setName(t);
            for (Linear_Event linear_event : this.linearEventReposiory.findAll()) {
                if(le.getPkd() != null && le.getPkf() != null){


                if(linear_event.getPkd() > le.getPkd() && linear_event.getPkf() < le.getPkf()){

                    if(linear_event.getVoie() == voie){

                        if(linear_event.getRoute_name().equals(rtName)){
                            System.out.println("hana dkhelt l rtName");
                            if(linear_event.getEvent_type().getName().equals(t)){
                                CoordinateSynoptique crrd = new CoordinateSynoptique();
                                crrd.setX(t);
                                crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                ds.getData().add(crrd);

                            }
                        }
                  }
                }
                }
            }

            for (Ponctuel_Events ponctuel_event : this.ponctuelEventReposiotory.findAll()) {
                if(ponctuel_event.getPkEvent() > le.getPkd() && ponctuel_event.getPkEvent() < le.getPkf()){

                    if(ponctuel_event.getVoie() == voie){
                      if(ponctuel_event.getRoute_name() != null){
                          if(ponctuel_event.getRoute_name().equals(rtName)){
                              System.out.println("hana dkhelt l rtName");
                              if(ponctuel_event.getEvent_type().getName().equals(t)){
                                  CoordinateSynoptique crrd = new CoordinateSynoptique();
                                  crrd.setX(t);
                                  crrd.setY(List.of(ponctuel_event.getPkEvent()  ,(ponctuel_event.getPkEvent())+ 10));
                                  ds.getData().add(crrd);

                              }
                          }
                      }

                   }
                }
            }


            dss.add(ds);
        }


        System.out.println(dss);
        return dss;
    }



    public List<DataSynoptique> createAdvancedSynoptique(Synoptique_param le) {
        EventType event_type = this.eventTypeRepository.findEventTypeName(le.getEvent());
        System.out.println("achraf aouad");
        System.out.println(event_type);
        Date dt=new Date();
        Integer year=dt.getYear() + 1900;
        List<Integer> years = new ArrayList<>();
        years.add(year);
        for(int i =1;i<10;i++){
            years.add(year-i);
        }


        String rtName =  le.getRouteName();
        int voie = le.getVoie();



        List<DataSynoptique> dss = new ArrayList<>();

        for(Integer t : years){
            DataSynoptique ds = new DataSynoptique();
            ds.setName(Integer.toString(t));
            for (Linear_Event linear_event : this.linearEventReposiory.findAll()) {
                if(linear_event.getPkd() > le.getPkd() && linear_event.getPkf() < le.getPkf()){
                   if((linear_event.getDate_ajoute().getYear()+1900) == t ){
                    if(linear_event.getVoie() == voie){

                        if(linear_event.getRoute_name().equals(rtName)){
                            System.out.println("hana dkhelt l rtName");
                            if(linear_event.getEvent_type().equals(event_type)){
                                System.out.println("hana dkhelt l babkrk");
                                CoordinateSynoptique crrd = new CoordinateSynoptique();
                                crrd.setX(Integer.toString(t));
                                crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                ds.getData().add(crrd);

                            }
                        }
                    }
                    }
                }
            }

            for (Ponctuel_Events ponctuel_event : this.ponctuelEventReposiotory.findAll()) {
                if(ponctuel_event.getPkEvent() > le.getPkd() && ponctuel_event.getPkEvent() < le.getPkf()){
                    if((ponctuel_event.getDate_ajoute().getYear()+1900) == t ){
                    if(ponctuel_event.getVoie() == voie){
                        if(ponctuel_event.getRoute_name() != null){
                            if(ponctuel_event.getRoute_name().equals(rtName)){
                                System.out.println("hana dkhelt l rtName");
                                if(ponctuel_event.getEvent_type().equals(event_type)){
                                    System.out.println("hana dkhelt l babkrk");
                                    System.out.println("hana dkhelt l babkrk");
                                    CoordinateSynoptique crrd = new CoordinateSynoptique();
                                    crrd.setX(Integer.toString(t));
                                    crrd.setY(List.of(ponctuel_event.getPkEvent()  ,(ponctuel_event.getPkEvent())+ 10));
                                    ds.getData().add(crrd);

                                }
                            }
                            }
                        }

                    }
                }
            }


            dss.add(ds);
        }


        System.out.println(dss);
        return dss;
    }





    public List<DataSynoptique> createSynoptiqueLast10(Synoptique_param le) {

        Date dt=new Date();
        int year=dt.getYear() +1900;

        String rtName =  le.getRouteName();
        int voie = le.getVoie();
        List <EventType> evt = this.eventTypeRepository.findAll();
        List<String> eventsName = new ArrayList<>();
        for(EventType tt: evt){
            eventsName.add(tt.getName());
        }


        List<DataSynoptique> dss = new ArrayList<>();

        for(String t : eventsName){
            DataSynoptique ds = new DataSynoptique();
            ds.setName(t);

            for (Linear_Event linear_event : this.linearEventReposiory.findBetweenTwoDates()) {
                if(linear_event.getPkd() > le.getPkd() && linear_event.getPkf() < le.getPkf()){

                    if(linear_event.getVoie() == voie){

                        if(linear_event.getRoute_name().equals(rtName)){
                            System.out.println("hana dkhelt l rtName");
                            if(linear_event.getEvent_type().getName().equals(t)){
                                CoordinateSynoptique crrd = new CoordinateSynoptique();
                                crrd.setX(t);
                                crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                                ds.getData().add(crrd);

                           }
                        }
                    }
                }
            }

            for (Ponctuel_Events ponctuel_event : this.ponctuelEventReposiotory.findBetweenTwoDates()) {
                if(ponctuel_event.getPkEvent() > le.getPkd() && ponctuel_event.getPkEvent() < le.getPkf()){

                    if(ponctuel_event.getVoie() == voie){
                        if(ponctuel_event.getRoute_name() != null){
                            if(ponctuel_event.getRoute_name().equals(rtName)){
                                System.out.println("hana dkhelt l rtName");
                                if(ponctuel_event.getEvent_type().getName().equals(t)){
                                    CoordinateSynoptique crrd = new CoordinateSynoptique();
                                    crrd.setX(t);
                                    crrd.setY(List.of(ponctuel_event.getPkEvent()  ,(ponctuel_event.getPkEvent())+ 10));
                                    ds.getData().add(crrd);

                                }
                            }
                        }

                    }
                }
            }


            dss.add(ds);
        }


        System.out.println(dss);
        return dss;
    }





    public Mono<Resource> getVideo(String title){
        return Mono.fromSupplier(()->resourceLoader.
                getResource(String.format(FORMAT,title)))   ;
    }

    public String getPositionfeature(Double mesure , int voie,String route_name){
      return this.lrsRoutesRepository.getPositionfeature(mesure,voie,route_name);
    }


    public List<String> getdistinctValuesP(DistinctValue pp){
        String query = "select Distinct " + pp.getAttribute() + " from ponctuel_events where event_type_Id =" + pp.getThematqueId();
      return this.ponctuelEntityRepo.getdistinctValuesproc(query);
    }

    public List<String> getdistinctValuesPpchaD(Linear_Event pp){
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        String query = "select Distinct PKEVENT from ponctuel_events where route_name = " +  "\'" + pp.getRoute_name() + "\'" +  " and voie = "+ pp.getVoie();


        data1 =this.ponctuelEntityRepo.getdistinctValuesproc(query);


         query = "select Distinct PKD from linear_event where route_name = " +  "\'" + pp.getRoute_name() + "\'" +  " and voie = "+ pp.getVoie();


        data2 = this.linearEntityRepo.getdistinctValuesproc(query);
        data1.addAll(data2);
        return data1;

    }

    public List<String> getdistinctValuesPpchaDV(Linear_Event pp){
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        String query = "select Distinct PKEVENT from video where route_name = " +  "\'" + pp.getRoute_name() + "\'" +  " and voie = "+ pp.getVoie();


        data1 =this.ponctuelEntityRepo.getdistinctValuesproc(query);


         query = "select Distinct PKD from video where route_name = " +  "\'" + pp.getRoute_name() + "\'" +  " and voie = "+ pp.getVoie();


        data2 = this.ponctuelEntityRepo.getdistinctValuesproc(query);
        data1.addAll(data2);
        return data1;

    }


    public List<String> getdistinctValuesPpchaF(Linear_Event pp){
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        String query = "select Distinct PKEVENT from ponctuel_events where route_name = " +  "\'" + pp.getRoute_name() + "\'" + " and voie = "+ pp.getVoie();



        data1 = this.ponctuelEntityRepo.getdistinctValuesproc(query);

         query = "select Distinct PKF from linear_event where route_name = " +  "\'" + pp.getRoute_name() + "\'"+ " and voie = "+ pp.getVoie();

        data2 = this.linearEntityRepo.getdistinctValuesproc(query);


        data1.addAll(data2);
        return data1;
    }
//todo
    public List<String> getdistinctValuesPpchaFV(Linear_Event pp){
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        String query = "select Distinct PKEVENT from video where route_name = " +  "\'" + pp.getRoute_name() + "\'" + " and voie = "+ pp.getVoie();



        data1 = this.ponctuelEntityRepo.getdistinctValuesproc(query);

         query = "select Distinct PKF from video where route_name = " +  "\'" + pp.getRoute_name() + "\'"+ " and voie = "+ pp.getVoie();

        data2 = this.linearEntityRepo.getdistinctValuesproc(query);


        data1.addAll(data2);
        return data1;
    }

    public List<String> getdistinctValuesL(DistinctValue pp){
        String query = "select Distinct " + pp.getAttribute() + " from linear_event where event_type_Id = " + pp.getThematqueId();
      return this.linearEntityRepo.getdistinctValuesproc(query);
    }

    public Long getCurrentTime(videoScrolling videos){

      return this.lrsRoutesRepository.getPositionMesure(videos.getRoute(),videos.getX(),videos.getY(), videos.getVoie());
    }


    public Long getRouteId(String route_name , Double pkd,int voie){
      return this.lrsRoutesRepository.getRoute(route_name,pkd,voie);
    }

    public Optional<Lrs_routes> getRouteById(Long id ){
      return this.lrsRoutesRepository.findById(id);
    }

    public void saveVideo(Video vid){
       this.videoRepository.save(vid);
    }


    public List<Video> getVideosList(Linear_Event le){
        List<Lrs_routes> lrss = this.lrsRoutesRepository.findByNames(le.getRoute_name());
        List<Long> routes_id = new ArrayList<>();
        List<Video> videos = new ArrayList<>();

        for (Lrs_routes lee : lrss){
             routes_id.add(lee.getRoute_id());
        }


        for (Long id : routes_id){
            List<Video> vv =  this.videoRepository.queryByRoute(id,le.getVoie());
            for(Video v : vv){
                videos.add(v);
                System.out.println(v);
            }
        }

        return videos;


    }





}



