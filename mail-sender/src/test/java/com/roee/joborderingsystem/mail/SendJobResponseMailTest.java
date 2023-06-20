package com.roee.joborderingsystem.mail;

import com.roee.joborderingsystem.entities.Customer;
import com.roee.joborderingsystem.entities.Job;
import com.roee.joborderingsystem.entities.JobResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SendJobResponseMailTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private SendJobResponseMail sendJobResponseMail;

    @Test
    @DisplayName("verify sendJobResponseMail doesn't invoke emailService.send if customerEmail is null")
    void verifySendJobResponseMailDoesntInvokeEmailServiceSendIfCustomerEmailIsNull() {
        Customer customer = Instancio.create(Customer.class);
        customer.setEmail(null);
        Job job = Instancio.create(Job.class);
        job.setCustomer(customer);
        JobResponse jobResponse = Instancio.create(JobResponse.class);
        jobResponse.setJob(job);

        sendJobResponseMail.sendJobAcceptedMail(jobResponse);

        verify(emailService, never()).sendSimpleMessage(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("verify sendJobResponseMail invoke emailService.send for accepted job")
    void verifySendJobResponseMailInvokeEmailServiceSendForAcceptedJob() {
        JobResponse jobResponse = Instancio.create(JobResponse.class);
        jobResponse.setAccepted(true);

        sendJobResponseMail.sendJobAcceptedMail(jobResponse);

        Job job = jobResponse.getJob();
        verify(emailService).sendSimpleMessage(
            job.getCustomer().getEmail(),
            String.format("✅ Your job (%s#) was accepted", job.getId()),
            String.format("Your job (%s#) was accepted by %s", job.getId(), jobResponse.getWorker().getName())
        );
    }

    @Test
    @DisplayName("verify sendJobResponseMail invoke emailService.send for rejected job")
    void verifySendJobResponseMailInvokeEmailServiceSendForRejectedJob() {
        JobResponse jobResponse = Instancio.create(JobResponse.class);
        jobResponse.setAccepted(false);

        sendJobResponseMail.sendJobAcceptedMail(jobResponse);

        Job job = jobResponse.getJob();
        verify(emailService).sendSimpleMessage(
            job.getCustomer().getEmail(),
            String.format("❌ Your job (%s#) was rejected", job.getId()),
            String.format("Your job (%s#) was rejected by %s", job.getId(), jobResponse.getWorker().getName())
        );
    }
}
