package com.chirag;

import javax.swing.tree.DefaultTreeSelectionModel;

import com.chirag.dto.TestDto;

import reactor.core.publisher.Flux;

public class ClassDemo {

	private Flux<TestDto> generateFlux() {
		System.out.println("Inside generator : "+Thread.currentThread().getId());
		return Flux.just(new TestDto("d1"), new TestDto("d2"));
	}
	public static void main(String[] args) {
		ClassDemo obj = new ClassDemo();
		System.out.println("T1 : "+Thread.currentThread().getId());
		Flux<TestDto> fluxData = obj.generateFlux();
		System.out.println("T2 : "+Thread.currentThread().getId());
		fluxData.subscribe(d -> {
			System.out.println("Data is : "+d.getData()+" : "+Thread.currentThread().getId());
		});
	}
}