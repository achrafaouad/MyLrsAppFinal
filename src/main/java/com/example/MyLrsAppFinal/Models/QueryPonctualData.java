

package com.example.MyLrsAppFinal.Models;


        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.Entity;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QueryPonctualData {

    private Double thematique1;
    private Double thematique2;
    private Double pkEvent;

}
