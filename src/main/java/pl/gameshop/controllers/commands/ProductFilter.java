package pl.gameshop.controllers.commands;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter @Setter
public class ProductFilter {

    private String phrase;
    private Long platform;
    private Long category;

    @PositiveOrZero
    private BigDecimal minPrice;

    @PositiveOrZero
    private BigDecimal maxPrice;


    public boolean isEmpty(){
        return StringUtils.isEmpty(phrase) && platform == null && category == null && minPrice == null && minPrice == null;
    }

    public void clear(){
        this.phrase = "";
        this.category = null;
        this.platform = null;
        this.minPrice = null;
        this.maxPrice = null;
    }

    public String getPhraseLIKE(){
        if(StringUtils.isEmpty(phrase)) {
            return null;
        }else{
            return "%"+phrase+"%";
        }
    }


}
