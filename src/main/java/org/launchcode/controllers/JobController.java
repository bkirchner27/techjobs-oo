package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam("id") int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("job", jobData.findById(id));

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, @ModelAttribute Job newJob) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        newJob.setEmployer(jobData.findById(jobForm.getEmployerId()).getEmployer());
        newJob.setLocation(jobData.findById(jobForm.getLocationId()).getLocation());
        newJob.setPositionType(jobData.findById(jobForm.getPositionId()).getPositionType());
        newJob.setCoreCompetency(jobData.findById(jobForm.getCoreCompetencyId()).getCoreCompetency());

        if (errors.hasErrors()) {
            return "new-job";
        }


        jobData.add(newJob);
        int id = newJob.getId();


        return "redirect:/job/?id=" + id;

    }
}
