package pt.unl.fct.privproxy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pt.unl.fct.privproxy.exception.MethodNotImplementedException;
import pt.unl.fct.privproxy.exception.NotFoundException;
import pt.unl.fct.privproxy.model.AnalyzeViewResult;
import pt.unl.fct.privproxy.model.ProxyViewResume;
import pt.unl.fct.privproxy.model.ViewResult;
import pt.unl.fct.privproxy.service.ViewService;

import java.util.ArrayList;

@RestController
public class ViewApiImp implements ViewApi{

    @Autowired
    private ViewService viewService;


    @Override
    public ArrayList<ProxyViewResume> listAvailableViews() {
        return viewService.listAvailableViews();
    }

    @Override
    public ViewResult readView(String viewID) {
        try{
            return viewService.readView(viewID);
        } catch(NotFoundException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "View not found: " + viewID, e);
        } catch(MethodNotImplementedException e){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Aggregate function not permitted.", e);
        }
    }

    @Override
    public AnalyzeViewResult readViewAnalyze(String viewID) {
        return viewService.readViewAnalyze(viewID);
    }
}
