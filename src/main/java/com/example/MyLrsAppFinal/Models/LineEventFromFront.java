
package com.example.MyLrsAppFinal.Models;

        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.Column;
        import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LineEventFromFront {

    private String route_name;
    private  Double pkd;
    private  Double pkf;
    private int voie;

    private String c1;
    private String c2;
    private String c3;

    private Double d1;
    private Double d2;
    private Double d3;

    private Date t1;
    private Date t2;
    private Date t3;

    private String Actif;

    private String eventType;





}
