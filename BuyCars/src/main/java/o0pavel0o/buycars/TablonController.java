package o0pavel0o.buycars;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TablonController {

	@Autowired
	private Usuario usuario;

	private List<Anuncio> anuncios = new ArrayList<>();

	public TablonController() {
		anuncios.add(new Anuncio("Maria", "Quiero vender mi coche BMW 320D", "20000€"));
		anuncios.add(new Anuncio("Carlos", "Compro BMW", "10000€"));
		anuncios.add(new Anuncio("Roberto", "Vendo Mercedes", "15000€"));
	}

	@GetMapping("/")
	public String tablon(Model model, HttpSession session) {

		model.addAttribute("anuncios", anuncios);
		model.addAttribute("BuyCars", session.isNew());

		return "tablon";
	}

	@PostMapping("/anuncio/nuevo")
	public String nuevoAnuncio(Model model, Anuncio anuncio) {

		anuncios.add(anuncio);

		usuario.setNombre(anuncio.getNombre());
		usuario.incAnuncios();

		return "anuncio_guardado";

	}

	@GetMapping("/anuncio/nuevo_form")
	public String nuevoAnuncioForm(Model model) {

		model.addAttribute("nombre", usuario.getNombre());
		model.addAttribute("num_anuncios", usuario.getNumAnuncios());

		return "nuevo_anuncio";
	}

	@GetMapping("/anuncio/{num}")
	public String nuevoAnuncio(Model model, @PathVariable int num) {

		Anuncio anuncio = anuncios.get(num - 1);

		model.addAttribute("anuncio", anuncio);

		return "ver_anuncio";
	}
}