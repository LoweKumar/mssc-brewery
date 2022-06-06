package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */

@RequestMapping("api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    //Constructor DI
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<>(beerService.getBeerById(beerId),HttpStatus.OK);

    }

    @PostMapping
    //if @RequestBody we will not use then the object will be made but properties of it will not be
    //bound to it i.e. the object properties will have null values - empty object
    public ResponseEntity handlePost(@RequestBody BeerDto beerDto)
    {
        BeerDto saveDto = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location","/api/v1/beer"+saveDto.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);

    }

    @PutMapping({"/{beerId}"})
    //if @RequestBody we will not use then the object will be made but properties of it will not be
    //bound to it i.e. the object properties will have null values - empty object
    public ResponseEntity handleUpdate(@PathVariable("beerId") UUID beerId,@RequestBody BeerDto beerDto)
    {
        beerService.updateBeer(beerId,beerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)// similar like - return new ResponseEntity(HttpStatus.NO_CONTENT)
    // we use ResponseEntity to provide additional information like Header as shown in PostMapping above
    public void deleteBeer(@PathVariable("beerId") UUID beerId)
    {
        beerService.deleteById(beerId);

    }
}
