package com.bloodbank.bbdms.config;

import com.bloodbank.bbdms.model.*;
import com.bloodbank.bbdms.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PageContentRepository pageContentRepository;
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${app.admin.default-username}")
    private String defaultUsername;

    @Value("${app.admin.default-password}")
    private String defaultPassword;

    public DataSeeder(PageContentRepository pageContentRepository,
                       AdminRepository adminRepository) {
        this.pageContentRepository = pageContentRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) {
        seedAdmin();
        seedPageContents();
    }

    private void seedAdmin() {
        if (adminRepository.findByUsername(defaultUsername).isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername(defaultUsername);
            admin.setPassword(passwordEncoder.encode(defaultPassword));
            adminRepository.save(admin);
        }
    }

    private void seedPageContents() {
        if (pageContentRepository.count() > 0) return;

        addPage("Why Become Donor", "donor",
                "Blood is the most precious gift that anyone can give to another person \u2014 the gift of life. " +
                "A decision to donate your blood can save a life, or even several if your blood is separated into " +
                "its components \u2014 red cells, platelets and plasma \u2014 which can be used individually for patients " +
                "with specific conditions. Safe blood saves lives and improves health. Blood transfusion is needed for: " +
                "Women with complications of pregnancy, such as ectopic pregnancies and haemorrhage before, during or " +
                "after childbirth. Children with severe anaemia often resulting from malaria or malnutrition. People " +
                "with severe trauma following man-made and natural disasters. Many complex medical and surgical " +
                "procedures and cancer patients.");

        addPage("About Us", "aboutus",
                "Blood bank is a place where blood bag that is collected from blood donation events is stored in one " +
                "place. The term \u201cblood bank\u201d refers to a division of a hospital laboratory where the storage of " +
                "blood product occurs and where proper testing is performed to reduce the risk of transfusion related " +
                "events. The process of managing the blood bag that is received from the blood donation events needs " +
                "a proper and systematic management. The blood bag must be handled with care and treated thoroughly " +
                "as it is related to someone's life. The development of Web-based Blood Bank And Donation Management " +
                "System (BBDMS) is proposed to provide a management functional to the blood bank in order to handle " +
                "the blood bag and to make entries of the individuals who want to donate blood and who are in need.");

        addPage("The Need For Blood", "needforblood",
                "There are many reasons patients need blood. A common misunderstanding about blood usage is that " +
                "accident victims are the patients who use the most blood. Actually, people needing the most blood " +
                "include those: 1) Being treated for cancer 2) Undergoing orthopedic surgeries 3) Undergoing " +
                "cardiovascular surgeries.");

        addPage("Why Donate Blood", "whydonate",
                "Blood is the most precious gift that anyone can give to another person \u2014 the gift of life. A " +
                "decision to donate your blood can save a life, or even several if your blood is separated into its " +
                "components \u2014 red cells, platelets and plasma \u2014 which can be used individually for patients with " +
                "specific conditions. It is also needed for regular transfusions for people with conditions such as " +
                "thalassaemia and sickle cell disease and is used to make products such as clotting factors for " +
                "people with haemophilia. There is a constant need for regular blood supply because blood can be " +
                "stored for only a limited time before use.");

        addPage("Blood Groups", "bloodgroups",
                "Blood group of any human being will mainly fall in any one of the following groups: A positive or A " +
                "negative, B positive or B negative, O positive or O negative, AB positive or AB negative. Your blood " +
                "group is determined by the genes you inherit from your parents. A healthy diet helps ensure a " +
                "successful blood donation, and also makes you feel better! The most common blood type is O, followed " +
                "by type A. Type O individuals are often called \u201cuniversal donors\u201d since their blood can be " +
                "transfused into persons with any blood type. Those with type AB blood are called \u201cuniversal " +
                "recipients\u201d because they can receive blood of any type. For emergency transfusions, blood group type " +
                "O negative blood is the variety of blood that has the lowest risk of causing serious reactions for " +
                "most people who receive it.");
    }

    private void addPage(String name, String type, String data) {
        PageContent p = new PageContent();
        p.setPageName(name);
        p.setPageType(type);
        p.setPageData(data);
        pageContentRepository.save(p);
    }
}
