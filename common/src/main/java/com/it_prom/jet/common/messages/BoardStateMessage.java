package com.it_prom.jet.common.messages;

import com.it_prom.jet.common.bean.Airport;
import com.it_prom.jet.common.bean.Source;
import com.it_prom.jet.common.bean.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirPortStateMessage extends Message{
    private Airport airport;

    public AirPortStateMessage(){
        this.source = Source.AIRPORT;
        this.type = Type.STATE;
    }

    public AirPortStateMessage(Airport airport){
        this();
        this.airport = airport;
    }

}
