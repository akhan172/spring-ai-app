package com.mod9.aiApp.service;

import com.mod9.aiApp.records.TicketAnalysis;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AiServiceTest {
    @Autowired
    private TicketAnalyser ticketAnalyser;
    @Test
    public void getAnalysis(){
        TicketAnalysis joke = ticketAnalyser.analysis("laptop", "this piece of shit is not turning on");
        System.out.println("Ticket Analysis");
        System.out.println("Category: "+joke.category());
        System.out.println("Priority: "+joke.priority());
        System.out.println("Summary: "+joke.summary());
        System.out.println("Suggestions: "+joke.suggestedActions());
    }
    @Autowired
    private AiService aiService;
    @Test
    public void getjoke(){
        String topic = "Hi my name is Abrar okay, feeling disgusted, repulsed, sickened, and deeply disappointed";
        System.out.println(aiService.askAi(1,"What's my name"));
    }


    @Autowired
    private VibeMatcher vibeMatcher;
    @Test
    public void getEmbed(){
        var res = vibeMatcher.getEmbed("Hello how are you");
        System.out.println(res.length);
        System.out.println(Arrays.toString(res));
    }
    @Test
    public void addEmbed(){
        vibeMatcher.ingestData();
    }
    @Test
    public void getvibe(){
        List<Document> songs = vibeMatcher.vibeSongs("feeling disgusted, repulsed, sickened, and deeply disappointed");
        for (var song: songs){
            System.out.println(song.getMetadata().get("title"));
        }
    }
}
