package survey.stepDefinitions;


import io.cucumber.java.Before;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.actors.Stage;


public class ParameterDefinitions {

    public Actor actor(String actorName){
        return OnStage.theActorCalled(actorName);
    }

    //@Before
    public void setTheStage(){
        OnStage.setTheStage(new OnlineCast());
    }
}
