package guru.springframework.msscbrewery.web.controller.v2;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.services.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {

    private final BeerServiceV2 beerServiceV2;

    //Constructor DI
    public BeerControllerV2(BeerServiceV2 beerServiceV2) {

        this.beerServiceV2 = beerServiceV2;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);

    }

    @PostMapping
    //if @RequestBody we will not use then the object will be made but properties of it will not be
    //bound to it i.e. the object properties will have null values - empty object
    public ResponseEntity handlePost(@RequestBody BeerDto beerDto)
    {
        BeerDtoV2 saveDto = beerServiceV2.saveNewBeer(beerDto);

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
        beerServiceV2.updateBeer(beerId,beerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)// similar like - return new ResponseEntity(HttpStatus.NO_CONTENT)
    // we use ResponseEntity to provide additional information like Header as shown in PostMapping above
    public void deleteBeer(@PathVariable("beerId") UUID beerId)
    {
        beerServiceV2.deleteById(beerId);

    }

}
