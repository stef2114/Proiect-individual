package ro.digitalnation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClasaController {
	
	@Autowired
	private ClientRepository clientrepository;
	
	@Autowired
	private ProdusRepository produsrepository;
	
	@GetMapping("/home")
	public String home() {
		return "hello";
	}
	
	@GetMapping("/test")
	public String test1() {
		return "test";
	}
	
	@GetMapping("/")
	public String home1() {
		return "hello";
	}
	
	public int aflareclient(String name)
	{
		int x=1;
		boolean ok=true;
		while(ok==true) {
			Optional<Client> c= clientrepository.findById(x);
			if(c.isPresent()) {
				String cl=c.get().getEmail();
				if(cl.equals(name)) {
					return x;
				}
				x++;
			}else {
				ok=false;
			}
		}
		return 0;
	}
	
	@GetMapping("/Client")
	public String getclient(@RequestParam(name="email",required=true) String email,
			@RequestParam(name="pasword",required=true) String pasword, Model model) {
		int id=aflareclient(email);
		if(id!=0) {
			Optional<Client> c= clientrepository.findById(id);
			Client cl=c.get();
			model.addAttribute("clientID", cl);
			return "afisareclient";
		}
		return "hello";
	}
	
	@GetMapping("/Client")
	public String getclient(Model model) {
		model.addAttribute("Client", new Client());
		return "checkcont";
	}
	
	@PostMapping("/valverif")
	public String valverif(@ModelAttribute Client client, Model model) {
		int id=aflareclient(client.getEmail());
		if(id!=0) {
			Optional<Client> c= clientrepository.findById(id);
			Client cl=c.get();
			String ps=c.get().getPasword();
			if(ps.equals(client.getPasword())) {
				model.addAttribute("clientID", cl);
				return "afisareclient";
			}else {
				return "Parola gresita!";
			}
		}
		return "redirect:hello?name="+client.getFirstname();
	}
	
	@GetMapping("/getprodus")
	public String getprodus(@RequestParam(name="id", required=true) Long id, Model model) {
		Optional<Produs> p= produsrepository.findById(id);
		if(p.isPresent()) {
			Produs pr=p.get();
			model.addAttribute("produsID", pr);
			return "redirect:hello?name="+pr.getName();
		}
		return "hello";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("Client", new Client());
		return "crearecont";
	}
	
	@PostMapping("/adduser")
	public String adduser(@ModelAttribute Client client, Model model) {
		clientrepository.save(client);
		return "redirect:hello?name="+client.getFirstname();
	}

}
