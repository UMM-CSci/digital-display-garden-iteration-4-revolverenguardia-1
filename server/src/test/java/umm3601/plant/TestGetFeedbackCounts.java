package umm3601.plant;

import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.PlantController;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by carav008 on 4/13/17.
 */
public class TestGetFeedbackCounts {

    private final static String databaseName = "data-for-testing-only";
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain();
        plantController = new PlantController(databaseName);
    }


    @Test
    public void TestGetJSONFeedbackForPlantByPlantId(){

        //Add flower likes and dislikes to a plant
        String jsonLike1 = "{like: true, id: \"16001.0\"}";
        String jsonLike2 = "{like: true, id: \"16001.0\"}";
        String jsonDislike1= "{like: false, id: \"16001.0\"}";
        plantController.addFlowerRating(jsonLike1,"first uploadId");
        plantController.addFlowerRating(jsonLike2,"first uploadId");
        plantController.addFlowerRating(jsonDislike1,"first uploadId");

        // Add a comment to the comment collection
        String json = "{ plantId: \"16001.0\", comment : \"Here is our comment for this test\" }";
        plantController.storePlantComment(json, "first uploadId");


        String counts = plantController.getJSONFeedbackForPlantByPlantID("16001.0","first uploadId");

        System.out.println(counts);

        assertEquals("this should be in json format","{ \"likeCount\" : 2 , \"dislikeCount\" : 1 , \"commentCount\" : 1}",counts);
//        assertEquals("the count should have 2 likes ", "\"likeCount\" : 2",counts.substring(2,17));
//        assertEquals("the count should have 1 dilikes ", "\"dislikeCount\" : 1",counts.substring(20,38));
//        assertEquals("the count should have 1 comments ","\"commentCount\" : 1", counts.substring(41,59));
    }

}
