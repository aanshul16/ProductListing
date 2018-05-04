package com.productlisting.root;

import com.productlisting.http.Communicator;
import com.productlisting.ui.fragment.ProductDetailFragment;
import com.productlisting.ui.fragment.ProductListingFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, Communicator.class})
public interface ApplicationComponent {

    void inject(ProductListingFragment target);
    void inject(ProductDetailFragment target);
}
