/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.agera.step1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.codelabs.agera.R;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Observable;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.util.ArrayList;
import java.util.List;


public class Step1Activity extends AppCompatActivity {

    private MutableRepository<String> stringRespo;
    private Updatable updatable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1);

        stringRespo = Repositories.mutableRepository("my mutable repository");
        updatable = () -> Log.d("My AGERA", stringRespo.get());
    }

    @Override
    protected void onStart() {
        super.onStart();

        stringRespo.addUpdatable(updatable);
        stringRespo.accept("hello my agera sample !!");
    }

    @Override
    protected void onStop() {
        stringRespo.removeUpdatable(updatable);
        super.onStop();
    }
}
