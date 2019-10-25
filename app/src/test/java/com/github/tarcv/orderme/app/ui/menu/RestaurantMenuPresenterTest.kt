package com.github.tarcv.orderme.app.ui.menu

import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.Bucket
import com.github.tarcv.orderme.app.di.AppComponent
import com.github.tarcv.orderme.core.data.entity.Category
import com.github.tarcv.orderme.core.data.entity.Dish
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox


@RunWith(PowerMockRunner::class)
@PrepareForTest(RestaurantMenuPresenter::class)
class RestaurantMenuPresenterTest {
    @Mock
    lateinit var component: AppComponent

    @Mock
    lateinit var view: RestaurantMenuView

    lateinit var presenter: RestaurantMenuPresenter

    private val placeId = 2
    private val categoryId = 4
    private val dishId = 42
    private val category = Category("TestCategory", categoryId, placeId)
    private val dish = Dish(dishId, "Delicious", "Dish", categoryId, 1.2)

    @get:Rule
    val mockitoRule: MethodRule = MockitoJUnit.rule()

    @Before
    fun setupPresenter() {
        App.component = component

        presenter = Whitebox.newInstance(RestaurantMenuPresenter::class.java).also {
            Whitebox.setInternalState(it, "category", category)
            it.bind(view)
        }
    }

    @Test
    fun minusButtonDoesntDecreaseCountBelowZero() {
        presenter.bucket = Bucket().apply {
            dishes = mutableMapOf(dish to 0)
        }

        presenter.onMinusButtonCLicked(dish)
        Assert.assertEquals(0, presenter.bucket.dishes[dish])

        verify(view, times(1)).refreshSum()
    }

    @Test
    fun minusButtonDecreasesCount() {
        presenter.bucket = Bucket().apply {
            dishes = mutableMapOf(dish to 4)
        }

        presenter.onMinusButtonCLicked(dish)
        Assert.assertEquals(3, presenter.bucket.dishes[dish])

        verify(view, times(1)).refreshSum()
    }

    @Test
    fun plusButtonIncreasesCount() {
        presenter.bucket = Bucket().apply {
            dishes = mutableMapOf(dish to 4)
        }

        presenter.onPlusButtonCLicked(dish)
        Assert.assertEquals(5, presenter.bucket.dishes[dish])

        verify(view, times(1)).refreshSum()
    }

    @Test
    fun plusButtonAddsNewDishWhenNotInBucket() {
        presenter.bucket = Bucket().apply {
            dishes = mutableMapOf(dish to 4)
        }

        val newDish = Dish(dishId + 1, "Delicious", "Dish 2", categoryId, 1.2)
        presenter.onPlusButtonCLicked(newDish)
        Assert.assertEquals(1, presenter.bucket.dishes[newDish])
        Assert.assertEquals(4, presenter.bucket.dishes[dish])

        verify(view, times(1)).refreshSum()
    }
}