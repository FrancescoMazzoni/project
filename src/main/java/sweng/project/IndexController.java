package sweng.project;

import java.io.File;
import java.util.Map;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class IndexController {
    
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/register")
    public String userRegistration(@ModelAttribute User user, Model model) {
        System.out.println(user.toString());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getSurname());

        File file = new File("database.db");
        DB db = DBMaker.newFileDB(file).make();

        Map<String, String> utentiMap = db.getHashMap("utenti");

        // Aggiunta di un utente al database
        String nome = user.getName();
        String cognome = user.getSurname();
        String email = user.getEmail();

        utentiMap.put(email, nome);
    
        // Commit dei cambiamenti e chiusura del database
        db.commit();

        System.out.println("Utente aggiunto con successo al database.");

        db.close();
        return "welcome";
    }
}
