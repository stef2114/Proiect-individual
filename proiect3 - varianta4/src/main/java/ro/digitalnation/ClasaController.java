package ro.digitalnation;

import java.util.ArrayList;
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import antlr.StringUtils;

@Controller
public class ClasaController {
	
	@Autowired
	private ClientRepository clientrepository;
	
	@Autowired
	private ProdusRepository produsrepository;
	
	String emailusser="", paswordusser="";
	
	@GetMapping("/home")
	public String home() {
		if(emailusser.isEmpty()) {
			return "home";
		}
		return "hoome";
	}
	
	@GetMapping("/productslist")
	public String Productlist(Model model) {
		Optional<Produs> p= produsrepository.findById(1L);
		if(p.isPresent()) {
			ArrayList<Produs> products= (ArrayList<Produs>) produsrepository.findAll();
			model.addAttribute("products", products);
			return "productslist";
		}
		return "redirect:mesaj?name=There are no products";
	}
	
	@GetMapping("/clientfavoritelist")
	public String clientfavoritelist(Model model) {
		if(emailusser.isEmpty())
			return "redirect:mesaj?name=Please login first";
		Long idc=aflareclient(emailusser);
		Optional<Client> c= clientrepository.findById(idc);
		ArrayList<Long> favlid=c.get().getFavorite();
		ArrayList<Produs> favlusser=new ArrayList<Produs>();
		for( Long client : favlid) {
			Optional<Produs> pr= produsrepository.findById(client);
			Produs pro=pr.get();
			favlusser.add(pro);
		}
		model.addAttribute("favlusser", favlusser);
		return "usserfavoritelist";
	}
	
	@GetMapping("/clientbasket")
	public String clientbasket(Model model) {
		if(emailusser.isEmpty())
			return "redirect:mesaj?name=Please login first";
		Long idc=aflareclient(emailusser);
		Optional<Client> c= clientrepository.findById(idc);
		ArrayList<Long> basketid=c.get().getBasket();
		int cost=0;
		ArrayList<Produs> basketusser=new ArrayList<Produs>();
		for( Long client : basketid) {
			Optional<Produs> pr= produsrepository.findById(client);
			Produs pro=pr.get();
			cost=cost+pro.getPrice();
			basketusser.add(pro);
		}
		model.addAttribute("basketusser", basketusser);
		model.addAttribute("price", cost);
		return "basketusserlist";
	}

	
	@GetMapping("/mesaj")
	public String mesaj(@RequestParam(name="name",required=true) String enunt, Model model) {
		model.addAttribute("enunt", enunt);
		return "message";
	}
	
	@GetMapping("/")
	public String home1() {
		if(emailusser.isEmpty()) {
			return "home";
		}
		return "hoome";
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
		if(paswordusser.equals(cl.getPasword()))
		{
			model.addAttribute("client", cl);
			return "afisareclient";
		}
		return "redirect:mesaj?name=Wrong pasword";
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
		emailusser="";
		paswordusser="";
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
			emailusser=client.getEmail();			paswordusser=client.getPasword();
			return "redirect:ClientAccount?id="+id;
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
			paswordusser=client.getPasword();
			Long id=aflareclient(client.getEmail());
			return "redirect:ClientAccount?id="+id;
		}
		return "redirect:mesaj?name=To this email already corresponds an account";
	}
	
	@GetMapping("/addfavls/{id}")
	public String addfavls(@PathVariable("id") Long id) {
		if(emailusser.isEmpty()) {
			return "redirect:/mesaj?name=Please login first";
		}
		Long idcl=aflareclient(emailusser);
		Optional<Client> c= clientrepository.findById(idcl);
		ArrayList<Long> ids;
		ids=c.get().getFavorite();
		ids.add(id);
		return "redirect:/mesaj?name=Product added to the Favorite List successfully!";
	}
	
	@GetMapping("/addtobasket/{id}")
	public String addtobasket(@PathVariable("id") Long id) {
		if(emailusser.isEmpty()) {
			return "redirect:/mesaj?name=Please login first";
		}
		Long idcl=aflareclient(emailusser);
		Optional<Client> c= clientrepository.findById(idcl);
		ArrayList<Long> ids;
		ids=c.get().getBasket();
		ids.add(id);
		return "redirect:/mesaj?name=Product added to the Basket successfully!";
	}
	
	@GetMapping("/deleteprod/{id}")
	public String DeleteProduct(@PathVariable("id") Long id) {
		produsrepository.deleteById(id);
		return "redirect:/productslist";
	}
	
	@GetMapping("/deleteprodfromfavorite/{id}")
	public String DeleteProductfromfavorite(@PathVariable("id") Long id) {
		Long idc=aflareclient(emailusser);
		Optional<Client> c= clientrepository.findById(idc);
		ArrayList<Long> cl=c.get().getFavorite();
		int idf=cl.indexOf(id);
		cl.remove(idf);
		return "redirect:/clientfavoritelist";
	}
	
	@GetMapping("/deleteprodfrombasket/{id}")
	public String DeleteProductfrombasket(@PathVariable("id") Long id) {
		Long idc=aflareclient(emailusser);
		Optional<Client> c= clientrepository.findById(idc);
		ArrayList<Long> cl=c.get().getBasket();
		int idb=cl.indexOf(id);
		cl.remove(idb);
		return "redirect:/clientbasket";
	}
	
	@GetMapping("/productaccount/{id}")
	public String contprodus1(@PathVariable("id") Long id, Model model) {
		Optional<Produs> p= produsrepository.findById(id);
		Produs pr=p.get();
		model.addAttribute("produs", pr);
		return "afisareprodus";	
	}

}
