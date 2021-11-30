package pt.unl.fct.privproxy.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import pt.unl.fct.privproxy.model.AnalyzeViewResult;
import pt.unl.fct.privproxy.model.ProxyViewResume;
import pt.unl.fct.privproxy.model.ViewResult;

import java.util.ArrayList;

@RequestMapping("/v1/view")
public interface ViewApi {

    @RequestMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.OK)
    ArrayList<ProxyViewResume> listAvailableViews();

    @RequestMapping(value = "/{viewID}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.OK)
    ViewResult readView(@PathVariable("viewID") String viewID);

    @RequestMapping(value = "/{viewID}/analyze",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.OK)
    AnalyzeViewResult readViewAnalyze(@PathVariable("viewID") String viewID);
}
