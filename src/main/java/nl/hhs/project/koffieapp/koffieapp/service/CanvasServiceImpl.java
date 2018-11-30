package nl.hhs.project.koffieapp.koffieapp.service;

import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;
import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Chair;
import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.model.payload.ApiResponse;
import nl.hhs.project.koffieapp.koffieapp.repository.CanvasRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.ChairRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CanvasServiceImpl implements CanvasService {

    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private CanvasRepository canvasRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse update(Canvas canvas) {

        boolean success = true;
        String returnMessage = "Canvas updated";
        Canvas canvasToUpdate = canvasRepository.getOne(canvas.getId());

        canvasToUpdate.setDescription(canvas.getDescription());
        canvasToUpdate.setDepartment(canvas.getDepartment());
        canvasToUpdate.setCoffeeMachine(null);

        for (Chair chair : canvas.getChairs()) {

            if (isNewChair(chair)) {
                chairRepository.save(chair);
                canvasToUpdate.addChair(chair);
            }

            else if (hasNoUser(chair)) {
                chairRepository.save(chair);
            }

            else {
                if (canBindUserToChair(chair)) {
                    chairRepository.save(chair);

                } else {
                    success = false;
                    returnMessage = "";
                }
            }
        }
        canvasRepository.save(canvasToUpdate);
        return new ApiResponse(success, returnMessage);
    }

    private boolean isNewChair(Chair chair) {
        return chair.getId() == 0;
    }

    private boolean hasNoUser(Chair chair) {
        return chair.getUser() == null;
    }

    private boolean canBindUserToChair(Chair chair) {

        String email = chair.getUser().getEmail();
        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isPresent()) {

            User userToBind = user.get();
            List<Chair> chairs = chairRepository.findChairByUser_Email(userToBind.getEmail());

            if (chairs.size() == 1) {
                // user already bound to another chair, switch chairs
                chairs.get(0).setUser(null);
                chair.setUser(userToBind);
                return true;

            } else {
                // bind user to chair
                chair.setUser(userToBind);
                return true;
            }
        }
        // no user found to bind
        return false;
    }

}
