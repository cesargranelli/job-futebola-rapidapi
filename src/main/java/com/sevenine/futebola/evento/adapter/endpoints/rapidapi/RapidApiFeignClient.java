package com.sevenine.futebola.evento.adapter.endpoints.rapidapi;

import com.sevenine.futebola.evento.adapter.endpoints.rapidapi.response.PlayerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(name = "rapidapi")
public interface RapidApiFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/team/{idTeam}/players", consumes = "application/json")
    Map<String, List<Map<String, PlayerResponse>>> getPlayers(@PathVariable("idTeam") Long idTeam);

}
