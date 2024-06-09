package com.sai.request;


import com.sai.model.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExternalRequest {
    private int floorNumber;
    private Direction direction;
}
