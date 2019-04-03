package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Task;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.GroupService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@RequestMapping(path="/group")
@RestController
public class GroupController extends BaseController {

    // This is the EJB used to access user data
    @Autowired
    GroupService groupService;

    @PostMapping(consumes = "application/json")
    public IdObject addGroup(HttpSession session, @Valid @RequestBody RegisterGroup group) {

        Long userId = getLoggedUser(session);

        if (group.groupName == null) {
            throw new ControllerException("No name supplied");
        }
        if (group.groupDescription == null) {
            throw new ControllerException("No description supplied");
        }

        return groupService.addGroup(group.groupName, userId, group.groupDescription);
    }

    @GetMapping
    public Collection<Group> listAllGroups(HttpSession session) {
        Long userId = getLoggedUser(session);

        return groupService.getGroups(userId);
    }


    // Class that represents the BODY of the POST request!
    static class RegisterGroup {

        @NotNull
        public String groupName;
        @NotNull
        public String groupDescription;
    }


}
