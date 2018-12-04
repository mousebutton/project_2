package nl.hhs.project.koffieapp.koffieapp.service;

import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;
import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Chair;
import nl.hhs.project.koffieapp.koffieapp.model.payload.ApiResponse;

public interface CanvasService {

    Canvas update(Canvas canvas);

    ApiResponse removeChair(long chairId, long canvasId);
}
