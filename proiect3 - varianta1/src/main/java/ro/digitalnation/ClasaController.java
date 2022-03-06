package ro.digitalnation;

import java.util.ArrayList;
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
		return "home";
	}
	
	String emailusser;
	
	@GetMapping("/mesaj")
	public String mesaj(@RequestParam(name="name",required=true) String enunt, Model model) {
		model.addAttribute("enunt", enunt);
		return "message";
	}
	
	@GetMapping("/")
	public String home1() {
		return "home";
	}
	
	public Long aflareclient(String name)
	{
		Long x=1L;
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
		return 0L;
	}
	
	public Long aflareprodus(String name)
	{
		Long x=1L;
		boolean ok=true;
		while(ok==true) {
			Optional<Produs> p= produsrepository.findById(x);
			if(p.isPresent()) {
				String pr=p.get().getName();
				if(pr.equals(name)) {
					return x;
				}
				x++;
			}else {
				ok=false;
			}
		}
		return 0L;
	}
	
	@GetMapping("/ClientAccount")
	public String contclient(@RequestParam(name="id",required=true) Long id, Model model) {
		Optional<Client> c= clientrepository.findById(id);
		Client cl=c.get();
		model.addAttribute("client", cl);
		return "afisareclient";
		
	}
	
	@GetMapping("/ProductAccount")
	public String contprodus(@RequestParam(name="id",required=true) Long id, Model model) {
		Optional<Produs> p= produsrepository.findById(id);
		Produs pr=p.get();
		model.addAttribute("produs", pr);
		return "afisareprodus";
		
	}
	
	@GetMapping("/logout")
	public String logout() {
		if(emailusser.isEmpty()) {
			return "redirect:mesaj?name=You already are logout";
		}
		emailusser="";
		return "home";
	}
	
	@GetMapping("/login")
	public String getclient(Model model) {
		if(emailusser.isEmpty()) {
			model.addAttribute("Client", new Client());
			return "checkcont";
		}
		Long id=aflareclient(emailusser);
		return "redirect:ClientAccount?id="+id;
	}
	
	@PostMapping("/valverif")
	public String valverif(@ModelAttribute Client client, Model model) {
		Long id=aflareclient(client.getEmail());
		if(id!=0) {
			Optional<Client> c= clientrepository.findById(id);
			Client cl=c.get();
			String ps=c.get().getPasword();
			if(ps.equals(client.getPasword())) {
				model.addAttribute("clientID", cl);
				return "redirect:ClientAccount?id="+id;
			}else {
				return "redirect:mesaj?name=Wrong pasword";
			}
		}
		return "redirect:mesaj?name=This email doesn't exist";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("Client", new Client());
		return "crearecont";
	}
	
	@GetMapping("/addproduct")
	public String addproduct(Model model) {
		model.addAttribute("Produs", new Produs());
		return "creareprodus";
	}
	
	@PostMapping("/addproduct1")
	public String addproduct1(@ModelAttribute Produs produs, Model model) {
		produsrepository.save(produs);
		return "redirect:mesaj?name=Product added successfully";
	}
	
	@GetMapping("/searchproduct")
	public String searchproduct(Model model) {
		model.addAttribute("Produs", new Produs());
		return "findproduct";
	}
	
	@PostMapping("/addproduct2")
	public String addproduct2(@ModelAttribute Produs name, Model model) {
		Long id=aflareprodus(name.getName());
		return "redirect:ProductAccount?id="+id;
	}
	
	@PostMapping("/adduser")
	public String adduser(@ModelAttribute Client client, Model model) {
		if(aflareclient(client.getEmail())==0L) {
			clientrepository.save(client);
			emailusser=client.getEmail();
			Long id=aflareclient(client.getEmail());
			return "redirect:ClientAccount?id="+id;
		}
		return "redirect:mesaj?name=To this email already corresponds an account";
	}
	
	@PostMapping("/addfavls")
	public String adduser(@RequestParam(name="name", required=true) String prname, 
			@RequestParam(name="email", required=true) String email) {
		Long idcl=aflareclient(email);
		Long idpr=aflareprodus(prname);
		Optional<Client> c= clientrepository.findById(idcl);
		ArrayList<Long> ids;
		ids=c.get().getFavorite();
		ids.add(idpr);
		return "redirect:mesaj?name=Product added to the Favorite List successfully!";
	}

}
