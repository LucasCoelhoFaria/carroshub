package com.autohub.api.controller.DashboardController;

import com.autohub.api.repository.*;
import com.autohub.api.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final BusinessRepository businessRepository;
    private final ServiceRepository serviceRepository;
    private final FaqRepository faqRepository;
    private final LandingSectionRepository sectionRepository;

    // ===========================
    // Página HTML da dashboard
    // ===========================
    @GetMapping("/{businessId}")
    public String dashboardPage(@PathVariable UUID businessId, Model model) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));

        model.addAttribute("business", business);
        model.addAttribute("services", serviceRepository.findByBusiness(business));
        model.addAttribute("faqs", faqRepository.findByBusiness(business));
        model.addAttribute("sections", sectionRepository.findByBusiness(business));

        return "dashboard"; // Thymeleaf template
    }

    // ===========================
    // Endpoints REST para Services
    // ===========================
    @GetMapping("/services/{businessId}")
    @ResponseBody
    public List<ServiceEntity> getServices(@PathVariable UUID businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));
        return serviceRepository.findByBusiness(business);
    }

    @PostMapping("/services/{businessId}")
    @ResponseBody
    public ServiceEntity createService(@PathVariable UUID businessId, @RequestBody ServiceEntity service) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));

        service.setBusiness(business);
        service.setEnabled(true);
        return serviceRepository.save(service);
    }

    @PutMapping("/services/{serviceId}")
    @ResponseBody
    public ServiceEntity updateService(@PathVariable Long serviceId, @RequestBody ServiceEntity updatedService) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        service.setName(updatedService.getName());
        service.setDescription(updatedService.getDescription());
        service.setEnabled(updatedService.isEnabled());
        return serviceRepository.save(service);
    }

    @DeleteMapping("/services/{serviceId}")
    @ResponseBody
    public void deleteService(@PathVariable Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }

    // ===========================
    // Endpoints REST para FAQs
    // ===========================
    @GetMapping("/faqs/{businessId}")
    @ResponseBody
    public List<Faq> getFaqs(@PathVariable UUID businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));
        return faqRepository.findByBusiness(business);
    }

    @PostMapping("/faqs/{businessId}")
    @ResponseBody
    public Faq createFaq(@PathVariable UUID businessId, @RequestBody Faq faq) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));
        faq.setBusiness(business);
        faq.setEnabled(true);
        return faqRepository.save(faq);
    }

    @PutMapping("/faqs/{faqId}")
    @ResponseBody
    public Faq updateFaq(@PathVariable Long faqId, @RequestBody Faq updatedFaq) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new RuntimeException("FAQ não encontrado"));

        faq.setQuestion(updatedFaq.getQuestion());
        faq.setAnswer(updatedFaq.getAnswer());
        faq.setEnabled(updatedFaq.isEnabled());
        return faqRepository.save(faq);
    }

    @DeleteMapping("/faqs/{faqId}")
    @ResponseBody
    public void deleteFaq(@PathVariable Long faqId) {
        faqRepository.deleteById(faqId);
    }

    // ===========================
    // Endpoints REST para Landing Sections (Hero/Footer/etc)
    // ===========================
    @PutMapping("/sections/{sectionId}")
    @ResponseBody
    public LandingSection updateSection(@PathVariable Long sectionId, @RequestBody Map<String, Object> content) {
        LandingSection section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Seção não encontrada"));
        section.setContent(content);
        return sectionRepository.save(section);
    }

}
