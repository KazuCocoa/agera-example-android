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

package com.example.android.codelabs.agera.step2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.codelabs.agera.R;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;

import java.util.Locale;

public class Step2Activity extends AppCompatActivity {

    public static final String VALUE_KEY = "VALUE_KEY";

    private final MutableRepository<Integer> valueRepository = Repositories.mutableRepository(0);

    private Repository<String> textValueRepository;

    private Updatable textValueUpdatable;

    private TextView valueTv;

    private Button incrementBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step2);

        valueTv = (TextView) findViewById(R.id.step2_value_tv);
        incrementBt = (Button) findViewById(R.id.increment_bt);

        // Set onClickListener
        incrementBt.setOnClickListener(view -> valueRepository.accept(valueRepository.get() + 1));

        // Create complex repository:
        textValueRepository = Repositories.repositoryWithInitialValue("N/A")
                .observe(valueRepository)
                .onUpdatesPerLoop()
                .getFrom(valueRepository)
                .thenTransform(input -> String.format(Locale.getDefault(), "%d", input))
                .compile();

        // Create updatable
        textValueUpdatable = () -> valueTv.setText(textValueRepository.get());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Add updatables here.
        textValueRepository.addUpdatable(textValueUpdatable);
    }

    @Override
    protected void onStop() {
        // Remove updatables here.
        textValueRepository.removeUpdatable(textValueUpdatable);
        super.onStop();
    }
}
