package info.androidhive.bottomnavigation.networkmodules;
import info.androidhive.bottomnavigation.networkmodules.moviemodels.BaseAllMovies;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MovieService {
    @GET("/movies_2017.json")
    Observable<BaseAllMovies> getGetAllCarExpenses();
}
