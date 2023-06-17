package com.roee.joborderingsystem;

import com.roee.joborderingsystem.entities.*;
import com.roee.joborderingsystem.repositories.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Profile("dev")
public class GenerateDevData {

    private final BusinessRepository businessRepository;

    private final WorkerRepository workerRepository;

    private final CustomerRepository customerRepository;

    private final JobRepository jobRepository;

    private final JobResponseRepository jobResponseRepository;

    @PostConstruct
    @Transactional
    void init() {
        Business bestServiceBusiness = businessRepository.save(Business.builder()
            .name("BestService.inc")
            .address(Address.builder()
                .street("142 Chapel St")
                .suburb("Saint Kilda")
                .city("Melbourne")
                .state("VIC")
                .postalCode("3182")
                .build()
            )
            .abn("12345678910")
            .acn("123456789")
            .build()
        );

        Customer robertCustomer = customerRepository.save(Customer.builder()
            .name("Robert De Niro")
            .address(Address.builder()
                .street("37 Windsor Ave")
                .suburb("McKinnon")
                .city("Melbourne")
                .state("VIC")
                .postalCode("3204")
                .build()
            )
            .email("roeegavriel+robert@gmail.com")
            .mobile("03 8533 8020")
            .business(bestServiceBusiness)
            .build()
        );

        Job designJob = jobRepository.save(Job.builder()
            .category("architecture")
            .description("design a full application architecture")
            .customer(robertCustomer)
            .dueDate(LocalDateTime.now().plusDays(30))
            .paymentMethod("cash")
            .price(20000).build()
        );

        Job backendJob = jobRepository.save(Job.builder()
            .category("backend")
            .description("raise a full backend system")
            .customer(robertCustomer)
            .dueDate(LocalDateTime.now().plusDays(60))
            .paymentMethod("cash")
            .price(10000).build()
        );

        Job webSiteJob = jobRepository.save(Job.builder()
            .category("web")
            .description("build web site")
            .customer(robertCustomer)
            .dueDate(LocalDateTime.now().plusDays(90))
            .paymentMethod("cash")
            .price(10000).build()
        );

        Business whiskyDevelopers = businessRepository.save(Business.builder()
            .name("The whisky freelance developers")
            .address(Address.builder()
                .street("96 Millers Road")
                .suburb("Altona North")
                .city("Melbourne")
                .state("VIC")
                .postalCode("3025")
                .build()
            )
            .acn("777777777")
            .build()
        );

        Worker johnnie = workerRepository.save(Worker.builder()
            .name("Johnnie Walker")
            .address(Address.builder()
                .street("3 Lowe Ave")
                .suburb("Altona North")
                .city("Melbourne")
                .state("VIC")
                .postalCode("3018")
                .build()
            )
            .email("johnnie@whisky.com")
            .business(whiskyDevelopers)
            .build()
        );
        jobResponseRepository.save(JobResponse.builder().worker(johnnie).job(designJob).accepted(true).build());
        jobResponseRepository.save(JobResponse.builder().worker(johnnie).job(backendJob).accepted(false).build());
        jobResponseRepository.save(JobResponse.builder().worker(johnnie).job(webSiteJob).accepted(false).build());

        Worker jack = workerRepository.save(Worker.builder()
            .name("Jack Daniels")
            .address(Address.builder()
                .street("9 Logan Ave")
                .suburb("Altona North")
                .city("Melbourne")
                .state("VIC")
                .postalCode("3018")
                .build()
            )
            .email("jack@whisky.com")
            .business(whiskyDevelopers)
            .build()
        );
        jobResponseRepository.save(JobResponse.builder().worker(jack).job(designJob).accepted(false).build());
        jobResponseRepository.save(JobResponse.builder().worker(jack).job(backendJob).accepted(true).build());

        Worker jameson = workerRepository.save(Worker.builder()
            .name("Jameson")
            .address(Address.builder()
                .street("19 Kearny Ave")
                .suburb("Altona North")
                .city("Melbourne")
                .state("VIC")
                .postalCode("3018")
                .build()
            )
            .email("jameson@whisky.com")
            .business(whiskyDevelopers)
            .build()
        );
        jobResponseRepository.save(JobResponse.builder().worker(jameson).job(webSiteJob).accepted(true).build());
    }
}
