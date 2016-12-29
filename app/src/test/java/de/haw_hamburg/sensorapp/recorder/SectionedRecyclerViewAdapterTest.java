package de.haw_hamburg.sensorapp.recorder;

import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by s.lange on 29.12.16.
 */
public class SectionedRecyclerViewAdapterTest {

    private SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter;

    @Before
    public void setUp() throws Exception {
        sectionedRecyclerViewAdapter = spy(SectionedRecyclerViewAdapter.class);
        when(sectionedRecyclerViewAdapter.getSectionCount()).thenReturn(2);
        when(sectionedRecyclerViewAdapter.getItemCountForSection(0)).thenReturn(3);
        when(sectionedRecyclerViewAdapter.getItemCountForSection(1)).thenReturn(2);
    }

    @Test
    public void onCreateViewHolder_TypeIsHeaderViewType_ShouldCreateHeaderViewHolder() {
        ViewGroup viewGroup = mock(ViewGroup.class);
        sectionedRecyclerViewAdapter.onCreateViewHolder(viewGroup, SectionedRecyclerViewAdapter.HEADER_VIEW_TYPE);

        verify(sectionedRecyclerViewAdapter).onCreateHeaderViewHolder(viewGroup);
    }

    @Test
    public void onCreateViewHolder_TypeIsItemViewType_ShouldCreateItemViewHolder() {
        ViewGroup viewGroup = mock(ViewGroup.class);
        sectionedRecyclerViewAdapter.onCreateViewHolder(viewGroup, SectionedRecyclerViewAdapter.ITEM_VIEW_TYPE);

        verify(sectionedRecyclerViewAdapter).onCreateItemViewHolder(viewGroup);
    }

    @Test
    public void onBindViewHolder_PositionIsHeader_ShouldBindHeaderViewHolder() {
        int expectedSection = 1;

        int position = 4;
        SectionedRecyclerViewAdapter.ViewHolder holder = mock(SectionedRecyclerViewAdapter.ViewHolder.class);
        sectionedRecyclerViewAdapter.onBindViewHolder(holder, position);

        verify(sectionedRecyclerViewAdapter).onBindHeaderViewHolder(holder, expectedSection);
    }

    @Test
    public void onBindViewHolder_PositionIsNotHeader_ShouldBindItemViewHolder() {
        int expectedSection = 1;
        int expectedSectionItem = 1;

        int position = 6;
        SectionedRecyclerViewAdapter.ViewHolder holder = mock(SectionedRecyclerViewAdapter.ViewHolder.class);
        sectionedRecyclerViewAdapter.onBindViewHolder(holder, position);

        verify(sectionedRecyclerViewAdapter).onBindItemViewHolder(holder, expectedSection, expectedSectionItem);
    }

    @Test
    public void getItemCount_ShouldReturnHeaderPlusItemsCount() {
        assertEquals(7, sectionedRecyclerViewAdapter.getItemCount());
    }

    @Test
    public void getItemViewType_PositionIsHeader_ShouldReturnHeaderViewType() {
        assertEquals(SectionedRecyclerViewAdapter.HEADER_VIEW_TYPE, sectionedRecyclerViewAdapter.getItemViewType(0));
        assertEquals(SectionedRecyclerViewAdapter.HEADER_VIEW_TYPE, sectionedRecyclerViewAdapter.getItemViewType(4));
    }

    @Test
    public void getItemViewType_PositionIsNotHeader_ShouldReturnItemViewType() {
        assertEquals(SectionedRecyclerViewAdapter.ITEM_VIEW_TYPE, sectionedRecyclerViewAdapter.getItemViewType(1));
        assertEquals(SectionedRecyclerViewAdapter.ITEM_VIEW_TYPE, sectionedRecyclerViewAdapter.getItemViewType(2));
        assertEquals(SectionedRecyclerViewAdapter.ITEM_VIEW_TYPE, sectionedRecyclerViewAdapter.getItemViewType(3));
        assertEquals(SectionedRecyclerViewAdapter.ITEM_VIEW_TYPE, sectionedRecyclerViewAdapter.getItemViewType(5));
        assertEquals(SectionedRecyclerViewAdapter.ITEM_VIEW_TYPE, sectionedRecyclerViewAdapter.getItemViewType(6));
    }
}