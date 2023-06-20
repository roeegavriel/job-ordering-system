package com.roee.joborderingsystem.mail;

import com.roee.joborderingsystem.entities.Customer;
import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SendJobResponseMail {

    private final String ACCEPTED_JOB_SUBJECT = "✅ Your job (%s#) was accepted";

    private final String REJECTED_JOB_SUBJECT = "❌ Your job (%s#) was rejected";

    private final EmailService emailService;

    public void sendJobAcceptedMail(JobResponse jobResponse) {
        Job job = jobResponse.getJob();
        Customer customer = job.getCustomer();
        String to = customer.getEmail();
        if (to == null) {
            return;
        }
        String subject = jobResponse.isAccepted() ? ACCEPTED_JOB_SUBJECT : REJECTED_JOB_SUBJECT;
        subject = String.format(subject, job.getId());

        String text = String.format("Your job (%s#) was %s by %s", job.getId(), jobResponse.isAccepted() ? "accepted" : "rejected", jobResponse.getWorker().getName());
        emailService.sendSimpleMessage(to, subject, text);
    }
}
