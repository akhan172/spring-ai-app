package com.mod9.aiApp.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class VibeMatcher {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private VectorStore vectorStore;

    public float[] getEmbed(String text){
        return embeddingModel.embed(text);
    }

    public void ingestData(){
        List<Document> songs = List.of(
                new Document("A deeply spiritual Sufi journey centered on cosmic creation and divine communion, drawing its foundation from the sacred Quranic command 'Be, and it is.' The song chronicles the transformation of a lost, unmoored soul who abandons worldly ego and sorrow at the shrine of Nizamuddin Auliya, finding solace, divine mercy, and purpose in the omnipresence of the Creator who fills every void.",
                        Map.of("title", "Kun Faya Kun", "genre", "Sufi / Devotional")),

                new Document("A fiery, raw narrative of class struggle and street rebellion embodying the voice of Mumbai's underground hip-hop movement. It chronicles an underprivileged youth's transition from systemic oppression, despair, and socioeconomic barriers into unyielding self-belief, defiantly asserting that destiny is not inherited but forged through hunger, hustle, and relentless grit.",
                        Map.of("title", "Apna Time Aayega", "genre", "Desi Hip-Hop / Rap")),

                new Document("A profound testament to selfless, unrequited devotion capturing the painful transition between acceptance and grief during a traditional wedding farewell. Rather than breeding bitterness, the singer transforms heart-wrenching loss into a prayer of blessings, treasuring memories in the chest of the heart and wishing complete fulfillment for the beloved while walking away empty-handed.",
                        Map.of("title", "Channa Mereya", "genre", "Ballad / Heartbreak")),

                new Document("An introspective romantic poem detailing the subtle, pervasive way love reconstructs a person's everyday existence. It portrays how routine transitions like the arrival of dusk, the coolness of the breeze, and the passing of days lose all standalone significance and become entirely intertwined with the presence, memory, and emotional gravity of a partner.",
                        Map.of("title", "Tum Se Hi", "genre", "Acoustic Pop / Romance")),

                new Document("An exuberant anthem of self-liberation and intoxicating hedonism set against a sun-soaked coastline. It revolves around shedding social decorum, self-consciousness, and conservative judgment to surrender completely to rhythm and impulse, symbolized by dancing so passionately and unreservedly that the ankle-bells snap.",
                        Map.of("title", "Ghungroo", "genre", "Disco / Funk-Pop")),

                new Document("A vivacious, theatrical slice of quintessential street-style celebration focused on cheeky courtship, vibrant bazaar culture, and flamboyant bravado. The song captures the spectacle of a charismatic lover brazenly winking and whistling across crowded city corners, sparking playful communal banter and irresistible dance rhythms.",
                        Map.of("title", "Aankh Maarey", "genre", "Desi Party / Masala Dance")),

                new Document("A satirical, deadpan critique of corporate modern life and workplace exhaustion, anthropomorphizing the dread of Monday as a ruthless, parasitic entity. Through comic frustration, it voices the universal misery of losing precious weekends to the endless grind of alarm clocks, demanding bosses, and draining nine-to-five obligations.",
                        Map.of("title", "Khoon Choos Le", "genre", "Ska / Comedy Rock")),

                new Document("An ecstatic, mystical celebration of romantic obsession and spiritual intoxication inspired by traditional Punjabi Sufi verses. Moving along the perpetual rhythm of a moving train, the song equates walking in the beloved's shadow with stepping into paradise, painting romance as a sacred fire that purifies, elevates, and sweeps the soul off the earth.",
                        Map.of("title", "Chaiyya Chaiyya", "genre", "Folk Fusion / World Beat")),

                new Document("A comforting philosophical ballad crafted for moments of absolute darkness, failure, and existential grief. It reminds the weary traveler that when external paths blur and hopes crumble, quiet inner surrender and humble faith reveal that divine guardianship has been surrounding and steering them all along.",
                        Map.of("title", "Aas Paas Khuda", "genre", "Inspirational Rock / Pop")),

                new Document("A sophisticated mid-century cabaret number delivering a street-smart, playful cautionary tale about urban romance. With seductive rhythm and sharp irony, it warns the wide-eyed newcomer that behind the sparkling facades and romantic promises of the modern city lie deceptive traps, broken vows, and fickle hearts.",
                        Map.of("title", "Babuji Dheere Chalna", "genre", "Vintage Cabaret / Retro Jazz"))
        );
        vectorStore.add(songs);
    }

    public List<Document> vibeSongs(String vibe){
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(vibe)
                .topK(3)
                .similarityThreshold(0.1)
                .build());
    }

}
