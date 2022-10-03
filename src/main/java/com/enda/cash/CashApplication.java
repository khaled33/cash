package com.enda.cash;

import com.enda.cash.UserDetails.Dao.Repository.AppUsersRepository;
import com.enda.cash.UserDetails.Dao.Repository.RolesRepository;
import com.enda.cash.agence.AgenceRepository;
import com.enda.cash.banque.BanquesRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@OpenAPIDefinition(info = @Info(title = "Enda Cash API", version = "1.0", description = "Enda cash Information"))
@SpringBootApplication

public class CashApplication extends ServletInitializer implements CommandLineRunner {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CashApplication.class);
    }

    @Autowired
    AppUsersRepository appUsersRepository;
    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    BanquesRepository banqueRepository;
    @Autowired
    AgenceRepository agenceRepository;
    /*VersementRepository versementRepository;
    AgenceRepository agenceRepository;
    RemboursementDataProviderT24 remboursementDataProviderT24;
    RemboursementRepository remboursementRepository ;
    *//*RemboursementServiceI remboursementServiceI ;*/

    public static void main(String[] args) {
        SpringApplication.run(CashApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder geBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // bean PathVariable Validatation
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofMillis(999999999)).setReadTimeout(Duration.ofMillis(999999999)).build();
        return restTemplate;

    }

    @Override
    public void run(String... args) throws Exception {
/*        AppUser appUser =new AppUser();
        appUser.setId(12345678L);
        appUser.setCin(12345678);
        appUser.setCode_pin(12345);
        appUser.setEmail("admin@admin.com");
        appUser.setNom("khaled");
        appUser.setPrenom("hizawi");
        appUser.setTel(28718388);
        appUser.setUsername("admin");
        appUser.setPassword(geBCryptPasswordEncoder().encode("123456"));
        appUser.setPwd_changed(false);
        appUser.setPin_changed(false);
        appUser.setEnable(false);
        appUser.setRoles(List.of(rolesRepository.findByRoleName(RoleName.ADMIN).get()));
        appUsersRepository.save(appUser);
*/
/*
        rolesRepository.save(new Role(1L, RoleName.ADMIN, "Admin Métier"));
        rolesRepository.save(new Role(2L, RoleName.METIER, "Métier"));
        rolesRepository.save(new Role(3L, RoleName.AP, "Agent à proximité (AP)"));

        banqueRepository.save(new Banque(57000L, "Agence", true, 3));
        banqueRepository.save(new Banque(32317634L, "STB", true, 1));
        banqueRepository.save(new Banque(32317863L, "AMEN", true, 1));
        banqueRepository.save(new Banque(32317871L, "BNA", true, 1));
        banqueRepository.save(new Banque(32317887L, "ABC", true, 1));
        banqueRepository.save(new Banque(32317898L, "ATTIJARI", true, 1));
        banqueRepository.save(new Banque(32317901L, "ATB", true, 1));
        banqueRepository.save(new Banque(32317936L, "BTL", true, 1));
        banqueRepository.save(new Banque(32317944L, "BTK", true, 1));
        banqueRepository.save(new Banque(32317952L, "QNB", true, 1));
        banqueRepository.save(new Banque(32317968L, "BIAT", true, 1));
        banqueRepository.save(new Banque(32317979L, "UBCI", true, 1));
        banqueRepository.save(new Banque(32317987L, "BTE", true, 1));
        banqueRepository.save(new Banque(32318002L, "CITY_BANK", true, 1));
        banqueRepository.save(new Banque(32318018L, "Poste", true, 2));
        banqueRepository.save(new Banque(32318029L, "UIB", true, 1));
        banqueRepository.save(new Banque(32318037L, "BT", true, 1));
        banqueRepository.save(new Banque(32351425L, "BH", true, 1));
        banqueRepository.save(new Banque(33840028L, "WIFAK_BANK", true, 1));





         agenceRepository.save(new Agences(155l,"Mifos HO "));
        agenceRepository.save(new Agences(156l,"Grand Tadhamen"));
        agenceRepository.save(new Agences(157l,"Omrane Superieur"));
        agenceRepository.save(new Agences(158l,"Grand Douar Hicher"));
        agenceRepository.save(new Agences(159l,"Grand Hrairia"));
        agenceRepository.save(new Agences(160l,"Grand Mhamdia"));
        agenceRepository.save(new Agences(161l,"Kabaria"));
        agenceRepository.save(new Agences(162l,"Medina"));
        agenceRepository.save(new Agences(163l,"Grand Ariana"));
        agenceRepository.save(new Agences(164l,"Grand Ben Arous"));
        agenceRepository.save(new Agences(165l,"Gafsa"));
        agenceRepository.save(new Agences(166l,"Sousse"));
        agenceRepository.save(new Agences(167l,"Kairouan"));
        agenceRepository.save(new Agences(168l,"Kasserine"));
        agenceRepository.save(new Agences(169l,"Sidi Bouzid"));
        agenceRepository.save(new Agences(170l,"Jendouba"));
        agenceRepository.save(new Agences(171l,"Sfax"));
        agenceRepository.save(new Agences(172l,"Gabès"));
        agenceRepository.save(new Agences(173l,"Béja"));
        agenceRepository.save(new Agences(174l,"Siliana"));
        agenceRepository.save(new Agences(175l,"El Kef"));
        agenceRepository.save(new Agences(176l,"Tozeur"));
        agenceRepository.save(new Agences(177l,"Cap-Bon"));
        agenceRepository.save(new Agences(178l,"Medenine"));
        agenceRepository.save(new Agences(179l,"Monastir"));
        agenceRepository.save(new Agences(180l,"Tunis_Nord"));
        agenceRepository.save(new Agences(181l,"Bizerte"));
        agenceRepository.save(new Agences(182l,"Tadhamen"));
        agenceRepository.save(new Agences(183l,"Mnihla"));
        agenceRepository.save(new Agences(184l,"Omrane"));
        agenceRepository.save(new Agences(185l,"Tahrir"));
        agenceRepository.save(new Agences(186l,"Douar Hicher"));
        agenceRepository.save(new Agences(187l,"Oued Ellil"));
        agenceRepository.save(new Agences(188l,"Hrairia"));
        agenceRepository.save(new Agences(189l,"Zouhour"));
        agenceRepository.save(new Agences(190l,"Sidi Hessine"));
        agenceRepository.save(new Agences(191l,"Mhamdia/Fouchana"));
        agenceRepository.save(new Agences(192l,"Kabaria"));
        agenceRepository.save(new Agences(193l,"Ouardia"));
        agenceRepository.save(new Agences(194l,"Jbel Jloud"));
        agenceRepository.save(new Agences(195l,"Medina"));
        agenceRepository.save(new Agences(196l,"Ariana-Borj Louzir"));
        agenceRepository.save(new Agences(197l,"Ben Arous"));
        agenceRepository.save(new Agences(198l,"Gafsa"));
        agenceRepository.save(new Agences(199l,"Sousse"));
        agenceRepository.save(new Agences(200l,"Kairouan"));
        agenceRepository.save(new Agences(201l,"Kasserine"));
        agenceRepository.save(new Agences(202l,"Sidi-Bouzid"));
        agenceRepository.save(new Agences(203l,"Jendouba"));
        agenceRepository.save(new Agences(204l,"Sfax"));
        agenceRepository.save(new Agences(205l,"Gabès"));
        agenceRepository.save(new Agences(206l,"Tebourba"));
        agenceRepository.save(new Agences(207l,"Béja"));
        agenceRepository.save(new Agences(208l,"Siliana"));
        agenceRepository.save(new Agences(209l,"El Kef"));
        agenceRepository.save(new Agences(210l,"Grombalia"));
        agenceRepository.save(new Agences(211l,"Tozeur"));
        agenceRepository.save(new Agences(212l,"Bir Lahfay"));
        agenceRepository.save(new Agences(213l,"Msaken"));
        agenceRepository.save(new Agences(214l,"Jerba"));
        agenceRepository.save(new Agences(215l,"Moknine"));
        agenceRepository.save(new Agences(216l,"El_Kram"));
        agenceRepository.save(new Agences(217l,"Marsa"));
        agenceRepository.save(new Agences(218l,"Bizerte"));
        agenceRepository.save(new Agences(219l,"Feriana"));
        agenceRepository.save(new Agences(220l,"Metlaoui"));
        agenceRepository.save(new Agences(221l,"Nabeul"));
        agenceRepository.save(new Agences(222l,"Akouda/Kalâa-Kbira"));
        agenceRepository.save(new Agences(223l,"Jbeniana"));
        agenceRepository.save(new Agences(224l,"Tajerouine"));
        agenceRepository.save(new Agences(225l,"Bargou"));
        agenceRepository.save(new Agences(226l,"Soliman"));
        agenceRepository.save(new Agences(227l,"Mornaguia"));
        agenceRepository.save(new Agences(228l,"Boussalem"));
        agenceRepository.save(new Agences(229l,"Hajeb Lâayoun"));
        agenceRepository.save(new Agences(230l,"Kébili"));
        agenceRepository.save(new Agences(231l,"Korba"));
        agenceRepository.save(new Agences(232l,"Hammamet"));
        agenceRepository.save(new Agences(233l,"Ksar Hellal"));
        agenceRepository.save(new Agences(234l,"Ksibet El Mediouni"));
        agenceRepository.save(new Agences(235l,"Hammam Lif"));
        agenceRepository.save(new Agences(236l,"El Guetar"));
        agenceRepository.save(new Agences(237l,"Monastir"));
        agenceRepository.save(new Agences(238l,"Menzel Bourguiba"));
        agenceRepository.save(new Agences(239l,"El Fahs"));
        agenceRepository.save(new Agences(240l,"Haffouz"));
        agenceRepository.save(new Agences(241l,"Regueb"));
        agenceRepository.save(new Agences(242l,"Menzel Temim"));
        agenceRepository.save(new Agences(243l,"Nefza"));
        agenceRepository.save(new Agences(244l,"Jammel"));
        agenceRepository.save(new Agences(245l,"El Hamma"));
        agenceRepository.save(new Agences(246l,"Medenine"));
        agenceRepository.save(new Agences(247l,"Sbikha"));
        agenceRepository.save(new Agences(248l,"Sbitla"));
        agenceRepository.save(new Agences(249l,"Makthar"));
        agenceRepository.save(new Agences(250l,"Tataouine"));
        agenceRepository.save(new Agences(251l,"Benguerdenne"));
        agenceRepository.save(new Agences(252l,"Zarzis"));
        agenceRepository.save(new Agences(253l,"Mahdia"));
        agenceRepository.save(new Agences(254l,"Région Kairouan"));
        agenceRepository.save(new Agences(255l,"Direction Régionale Centre-Sud"));
        agenceRepository.save(new Agences(256l,"Tataouine"));
        agenceRepository.save(new Agences(257l,"Mjez El Bab-Testour"));
        agenceRepository.save(new Agences(258l,"Zaghouan"));
        agenceRepository.save(new Agences(259l,"Direction Nord-Ouest"));
        agenceRepository.save(new Agences(260l,"Région Nord Ouest"));
        agenceRepository.save(new Agences(261l,"G Zaghouan"));
        agenceRepository.save(new Agences(262l,"Jelma"));
        agenceRepository.save(new Agences(263l,"Thala"));
        agenceRepository.save(new Agences(264l,"Menzel Bouzelfa"));
        agenceRepository.save(new Agences(265l,"Foussana"));
        agenceRepository.save(new Agences(266l,"El-Krib"));
        agenceRepository.save(new Agences(267l,"Sakiet Eddayer"));
        agenceRepository.save(new Agences(268l,"Souk Ejdid"));
        agenceRepository.save(new Agences(269l,"Morneg"));
        agenceRepository.save(new Agences(270l,"El Ksar"));
        agenceRepository.save(new Agences(271l,"Al Aghalba"));
        agenceRepository.save(new Agences(272l,"Sidi Thabet"));
        agenceRepository.save(new Agences(273l,"Mateur"));
        agenceRepository.save(new Agences(274l,"Béni Khalled"));
        agenceRepository.save(new Agences(275l,"Région Sfax"));
        agenceRepository.save(new Agences(276l,"Région Cap Bon"));
        agenceRepository.save(new Agences(277l,"Région Sud Ouest"));
        agenceRepository.save(new Agences(278l,"Région Tunis1"));
        agenceRepository.save(new Agences(279l,"Région Tunis2"));
        agenceRepository.save(new Agences(280l,"Région Tunis3"));
        agenceRepository.save(new Agences(281l,"Région Kasserine"));
        agenceRepository.save(new Agences(282l,"Région Nord Ouest2"));
        agenceRepository.save(new Agences(283l,"Région Sidi Bouzid"));
        agenceRepository.save(new Agences(284l,"Région Sud Est"));
        agenceRepository.save(new Agences(285l,"Région Bizerte"));
        agenceRepository.save(new Agences(286l,"Région Sahel1"));
        agenceRepository.save(new Agences(287l,"Région Sahel2"));
        agenceRepository.save(new Agences(288l,"Ras Jebal"));
        agenceRepository.save(new Agences(289l,"El Jem"));
        agenceRepository.save(new Agences(290l,"Fernana"));
        agenceRepository.save(new Agences(291l,"Sned"));
        agenceRepository.save(new Agences(292l,"Nefta"));
        agenceRepository.save(new Agences(293l,"Douz"));
        agenceRepository.save(new Agences(294l,"Sejnene"));
        agenceRepository.save(new Agences(295l,"Amdoune"));
        agenceRepository.save(new Agences(296l,"El Alia"));
        agenceRepository.save(new Agences(297l,"Sbiba"));
        agenceRepository.save(new Agences(298l,"Enfidha"));
        agenceRepository.save(new Agences(299l,"Mareth"));
        agenceRepository.save(new Agences(300l,"Kélibia"));

*/

        /*   remboursementDataProviderT24.RestTemplate_ConsulPay("AA213553PGCS");*/


    }


}
