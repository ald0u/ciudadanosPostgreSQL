package com.ciudadanos.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciudadanos.modelo.Ciudadano;
import com.ciudadanos.repositorio.CiudadanoRepositorio;

@Controller
public class CiudadanoControlador {

	@Autowired
	private CiudadanoRepositorio ciudadanoRepositorio;
	
	@GetMapping({"/",""})
	public String verPaginaDeInicio(Model modelo) {
		List<Ciudadano> ciudadanos = ciudadanoRepositorio.findAll();
		modelo.addAttribute("ciudadanos", ciudadanos);
		return "index";
	}
	
	@GetMapping("/nuevo")
	public String mostrarFormularioDeRegistrarCiudadano(Model modelo) {
		modelo.addAttribute("ciudadano", new Ciudadano());
		return "nuevo";
	}
	 
	@PostMapping("/nuevo")
	public String guardarCiudadano(@Validated Ciudadano ciudadano,BindingResult bindingResult,RedirectAttributes redirect,Model modelo) {
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("ciudadano", ciudadano);
			return "nuevo";
		}
		
		ciudadanoRepositorio.save(ciudadano);
		redirect.addFlashAttribute("msgExito", "El ciudadano ha sido agregado con exito");
		return "redirect:/";
	}
	

	@GetMapping("/{id}/editar")
	public String mostrarFormularioDeEditarCiudadano(@PathVariable Integer id,Model modelo) {
		Ciudadano ciudadano = ciudadanoRepositorio.getReferenceById(id);
		modelo.addAttribute("ciudadano", ciudadano);
		return "nuevo";
	}
	
	@PostMapping("/{id}/editar")
	public String actualizarCiudadano(@PathVariable Integer id,@Validated Ciudadano ciudadano,BindingResult bindingResult,RedirectAttributes redirect,Model modelo) {
		Ciudadano ciudadanoDB = ciudadanoRepositorio.getReferenceById(id);
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("ciudadano", ciudadano);
			return "nuevo";
		}
		
		ciudadanoDB.setNombre(ciudadano.getNombre());
		ciudadanoDB.setCelular(ciudadano.getCelular());
		ciudadanoDB.setDomicilio(ciudadano.getDomicilio());
		ciudadanoDB.setEmail(ciudadano.getEmail());
		ciudadanoDB.setFechaNacimiento(ciudadano.getFechaNacimiento());
		
		
		ciudadanoRepositorio.save(ciudadanoDB);
		redirect.addFlashAttribute("msgExito", "El ciudadano ha sido actualizado correctamente");
		return "redirect:/";
	}
	
	@PostMapping("/{id}/eliminar")
	public String eliminarCiudadano(@PathVariable Integer id,RedirectAttributes redirect) {
		Ciudadano ciudadano = ciudadanoRepositorio.getReferenceById(id);
		ciudadanoRepositorio.delete(ciudadano);
		redirect.addFlashAttribute("msgExito", "El ciudadano ha sido eliminado correctamente");
		return "redirect:/";
	}
}
