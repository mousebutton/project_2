package nl.hhs.project.koffieapp.koffieapp.service;

import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;
import nl.hhs.project.koffieapp.koffieapp.model.payload.ApiResponse;

public interface CanvasService {

    ApiResponse update(Canvas canvas);
}
